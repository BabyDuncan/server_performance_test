# 测试一下thrift协议在搜狐的生产环境中的性能

namespace java com.sohu.saccount.thrift

struct TestModel{
    1: string payload
}

service TestService{
    string  getPayLoad();
}