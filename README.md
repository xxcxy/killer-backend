[![Build Status](https://travis-ci.org/xxcxy/killer-backend.png)](https://travis-ci.org/xxcxy/killer-backend)

# killer-backend

这是一个``狼人杀``游戏的后端服务，前端应用使用 ``websocket`` 方式与后端服务交互。

## Checking out and Building
-----

```
git clone git@github.com:xxcxy/killer-backend.git
cd killer-backend
./gradlew idea
```

## 后端目前提供的服务

* 创建游戏房间
* 加入指定房间
* 离开房间
* 开始游戏
* 与玩家交互游戏流程
* 判断游戏是否结束

----------------------------------

## 与前端应用交互的消息协议

### 玩家投票消息格式

### 流程进入下一阶段的通知格式

### 玩家被杀的通知格式
