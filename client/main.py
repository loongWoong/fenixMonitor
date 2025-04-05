import psutil
import time
import requests
import json
import socket
import netifaces
import uuid

class SystemMonitor:
    def __init__(self, server_url):
        self.server_url = server_url
    
    def get_cpu_usage(self):
        return psutil.cpu_percent(interval=1)
    
    def get_memory_usage(self):
        try:
            mem = psutil.virtual_memory()
            return {
                'total': mem.total,
                'available': mem.available,
                'percent': mem.percent if mem.percent is not None else 0.0
            }
        except Exception as e:
            print(f"Error getting memory usage: {e}")
            return {
                'total': 0,
                'available': 0,
                'percent': 0.0
            }
    
    def get_network_usage(self):
        net_io = psutil.net_io_counters()
        return {
            'bytes_sent': net_io.bytes_sent,
            'bytes_recv': net_io.bytes_recv
        }
    
    def get_top_processes(self, n=10):
        processes = []
        for proc in psutil.process_iter(['pid', 'name', 'cpu_percent', 'memory_percent']):
            try:
                proc_info = {
                    'pid': proc.info['pid'],
                    'name': proc.info['name'],
                    'cpu_percent': proc.info['cpu_percent'] or 0.0,
                    'memory_percent': proc.info['memory_percent'] or 0.0
                }
                processes.append(proc_info)
            except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
                pass
        
        # 按CPU使用率排序
        processes.sort(key=lambda p: p['cpu_percent'], reverse=True)
        return processes[:n]
    
    def get_node_identifier(self):
        try:
            # 获取所有网络接口
            interfaces = netifaces.interfaces()
            ip_addresses = []
            mac_addresses = []
            
            for iface in interfaces:
                # 跳过回环接口
                if iface == 'lo' or iface.startswith('lo'):
                    continue
                    
                # 获取接口地址信息
                addrs = netifaces.ifaddresses(iface)
                
                # 获取IPv4地址
                if netifaces.AF_INET in addrs:
                    for addr in addrs[netifaces.AF_INET]:
                        ip = addr['addr']
                        if not ip.startswith('127.'):
                            ip_addresses.append(ip)
                            
                # 获取MAC地址
                if netifaces.AF_LINK in addrs:
                    mac = addrs[netifaces.AF_LINK][0]['addr']
                    if mac and mac != '00:00:00:00:00:00':
                        mac_addresses.append(mac)
            
            # 如果找到了IP和MAC地址，使用第一个非本地IP和对应的MAC地址
            if ip_addresses and mac_addresses:
                return f"{ip_addresses[0]}_{mac_addresses[0]}"
            
            # 如果没有找到合适的网络接口信息，使用UUID作为备选标识符
            node_uuid = str(uuid.uuid1())
            return f"uuid_{node_uuid}"
            
        except Exception as e:
            print(f"Error getting node identifier: {e}")
            return f"unknown_{str(uuid.uuid4())}"

    def collect_data(self):
        memory = self.get_memory_usage()
        network = self.get_network_usage()
        return {
            'nodeIdentifier': self.get_node_identifier(),
            'timestamp': int(time.time()),
            'cpu': self.get_cpu_usage(),
            'memoryTotal': memory['total'],
            'memoryAvailable': memory['available'],
            'memoryPercent': memory['percent'],
            'networkBytesSent': network['bytes_sent'],
            'networkBytesRecv': network['bytes_recv'],
            'processes': self.get_top_processes()
        }
    
    def send_data(self, data):
        try:
            response = requests.post(
                f"{self.server_url}/api/monitor",
                json=data,
                headers={'Content-Type': 'application/json'}
            )
            return response.status_code == 200
        except Exception as e:
            print(f"Error sending data: {e}")
            return False

if __name__ == "__main__":
    # 替换为实际的服务器URL
    monitor = SystemMonitor("http://192.168.31.45:8080")
    while True:
        data = monitor.collect_data()
        print(f"Collected data: {data}")
        if monitor.send_data(data):
            print("Data sent successfully")
        else:
            print("Failed to send data")
        time.sleep(5)  # 每5秒采集一次数据