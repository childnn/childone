1. 直接使用 maven-protoc 插件
2. 安装并配置 protoc, 配置环境变量
vim ~/.bash_profile

// 将/opt/protoc/bin加入到环境变量中

// 将go 安装的bin插件配置到环境变量中，可通过go env查看GOPATH=/root/go/，如 /root/go/bin

// 如 PATH=$PATH:$HOME/bin:/opt/protoc/bin:/opt/go/bin:/root/go/bin

source ~/.bash_profile



https://github.com/protocolbuffers/protobuf/releases