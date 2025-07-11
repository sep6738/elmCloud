---
title: elmCloud
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# elmCloud

Base URLs:

# Authentication

- HTTP Authentication, scheme: bearer

# 1. 用户服务 - User Service

## POST 发送短信验证码

POST /api/user/sms/send

为注册、登录、重置密码等场景发送手机验证码。

> Body 请求参数

```json
{
  "phone": "13800138000",
  "type": "register"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» phone|body|string| 否 |none|
|» type|body|string| 否 |none|

#### 枚举值

|属性|值|
|---|---|
|» type|register|
|» type|login|
|» type|reset|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|发送成功|None|

## POST 用户注册

POST /api/user/register

> Body 请求参数

```json
{
  "phone": "13800138001",
  "code": "123456",
  "password": "your_password",
  "nickname": "新用户"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» phone|body|string| 否 |none|
|» code|body|string| 否 |none|
|» password|body|string| 否 |none|
|» nickname|body|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|注册成功，返回用户信息和Token|None|

## POST 用户登录

POST /api/user/login

支持密码或验证码登录

> Body 请求参数

```json
{
  "phone": "13800138001",
  "loginType": "password",
  "password": "your_password",
  "code": "123456"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» phone|body|string| 否 |none|
|» loginType|body|string| 否 |none|
|» password|body|string| 否 |none|
|» code|body|string| 否 |none|

#### 枚举值

|属性|值|
|---|---|
|» loginType|password|
|» loginType|code|

> 返回示例

> 200 Response

```
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|登录成功，返回用户信息和Token|Inline|

### 返回数据结构

## GET 获取当前用户信息

GET /api/user/info

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## GET 获取用户地址列表

GET /api/user/addresses

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## POST 添加收货地址

POST /api/user/address

> Body 请求参数

```json
{
  "contactName": "张三",
  "contactPhone": "13900139000",
  "province": "云南省",
  "city": "昆明市",
  "district": "五华区",
  "detailAddress": "一二一大街文昌路68号",
  "longitude": 102.7,
  "latitude": 25.04
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» contactName|body|string| 否 |none|
|» contactPhone|body|string| 否 |none|
|» province|body|string| 否 |none|
|» city|body|string| 否 |none|
|» district|body|string| 否 |none|
|» detailAddress|body|string| 否 |none|
|» longitude|body|number(double)| 否 |none|
|» latitude|body|number(double)| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|添加成功|None|

## PUT 更新收货地址

PUT /api/user/address

> Body 请求参数

```json
{
  "id": 1,
  "contactName": "张三",
  "contactPhone": "13900139000",
  "province": "云南省",
  "city": "昆明市",
  "district": "五华区",
  "detailAddress": "一二一大街文昌路68号",
  "longitude": 102.7,
  "latitude": 25.04
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» id|body|integer(int64)| 否 |none|
|» contactName|body|string| 否 |none|
|» contactPhone|body|string| 否 |none|
|» province|body|string| 否 |none|
|» city|body|string| 否 |none|
|» district|body|string| 否 |none|
|» detailAddress|body|string| 否 |none|
|» longitude|body|number(double)| 否 |none|
|» latitude|body|number(double)| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|更新成功|None|

## DELETE 删除收货地址

DELETE /api/user/address/{addressId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|addressId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|删除成功|None|

## PUT 设置默认地址

PUT /api/user/address/{addressId}/default

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|addressId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|设置成功|None|

# 2. 商家与商品服务 - Store Service

## POST 获取商家列表

POST /api/store/list

根据地理位置、关键词等条件查询商家。

> Body 请求参数

```json
{
  "keyword": "咖啡",
  "longitude": 102.7,
  "latitude": 25.04,
  "pageNum": 1,
  "pageSize": 10,
  "sortBy": "distance"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» keyword|body|string| 否 |none|
|» longitude|body|number(double)| 否 |none|
|» latitude|body|number(double)| 否 |none|
|» pageNum|body|integer| 否 |none|
|» pageSize|body|integer| 否 |none|
|» sortBy|body|string| 否 |none|

#### 枚举值

|属性|值|
|---|---|
|» sortBy|rating|
|» sortBy|distance|
|» sortBy|sales|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|查询成功|None|

## GET 获取商家详情

GET /api/store/{storeId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|storeId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## GET 获取商家内商品分类

GET /api/store/{storeId}/product-categories

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|storeId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## POST 获取商家内商品列表

POST /api/store/products

> Body 请求参数

```json
{
  "storeId": 1,
  "categoryId": 10,
  "sortBy": "sales",
  "pageNum": 1,
  "pageSize": 20
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» storeId|body|integer(int64)| 否 |none|
|» categoryId|body|integer(int64)| 否 |none|
|» sortBy|body|string| 否 |none|
|» pageNum|body|integer| 否 |none|
|» pageSize|body|integer| 否 |none|

#### 枚举值

|属性|值|
|---|---|
|» sortBy|sales|
|» sortBy|price|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|查询成功|None|

## GET 获取商品详情

GET /api/store/product/{productId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|productId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

# 3. 订单服务 - Order Service

## GET 获取购物车列表

GET /api/order/cart

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## POST 添加商品到购物车

POST /api/order/cart/add

> Body 请求参数

```json
{
  "storeId": 1,
  "productId": 101,
  "productName": "拿铁",
  "unitPrice": 25,
  "quantity": 1
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» storeId|body|integer(int64)| 否 |none|
|» productId|body|integer(int64)| 否 |none|
|» productName|body|string| 否 |none|
|» unitPrice|body|number(double)| 否 |none|
|» quantity|body|integer| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|添加成功|None|

## PUT 更新购物车商品数量

PUT /api/order/cart/update

> Body 请求参数

```json
{
  "cartId": 234,
  "quantity": 3
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» cartId|body|integer(int64)| 否 |none|
|» quantity|body|integer| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|更新成功|None|

## DELETE 删除购物车商品

DELETE /api/order/cart/{cartId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|cartId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|删除成功|None|

## POST 创建订单

POST /api/order/create

> Body 请求参数

```json
{
  "storeId": 1,
  "addressId": 123,
  "paymentMethod": 1,
  "remark": "少冰，谢谢",
  "productAmount": 50,
  "actualAmount": 55,
  "orderItems": [
    {
      "productId": 101,
      "productName": "拿铁",
      "unitPrice": 25,
      "quantity": 2
    }
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» storeId|body|integer(int64)| 否 |none|
|» addressId|body|integer(int64)| 否 |none|
|» paymentMethod|body|integer| 否 |none|
|» remark|body|string| 否 |none|
|» productAmount|body|number(double)| 否 |none|
|» actualAmount|body|number(double)| 否 |none|
|» orderItems|body|[object]| 否 |none|
|»» productId|body|integer(int64)| 否 |none|
|»» productName|body|string| 否 |none|
|»» unitPrice|body|number(double)| 否 |none|
|»» quantity|body|integer| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|创建成功|None|

## POST 获取订单列表

POST /api/order/list

> Body 请求参数

```json
{
  "userId": 1,
  "status": 0,
  "pageNum": 1,
  "pageSize": 10
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» userId|body|integer(int64)| 否 |none|
|» status|body|integer| 否 |0-待支付, 1-待接单, ...|
|» pageNum|body|integer| 否 |none|
|» pageSize|body|integer| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

## GET 获取订单详情

GET /api/order/{orderId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|orderId|path|integer(int64)| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|None|

# 4. 支付服务 - Payment Service

## POST 创建支付

POST /payment/create

为一个订单创建支付请求，返回支付所需信息。

> Body 请求参数

```json
{
  "orderNo": "20231027100000123456",
  "userId": 1,
  "amount": 55,
  "paymentMethod": 1
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string| 否 |none|
|body|body|object| 否 |none|
|» orderNo|body|string| 否 |none|
|» userId|body|integer(int64)| 否 |none|
|» amount|body|number(double)| 否 |none|
|» paymentMethod|body|integer| 否 |1-支付宝, 2-微信|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|创建成功|None|

## GET 检查支付状态

GET /payment/status/{paymentNo}

前端轮询此接口以确认支付是否成功。

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|paymentNo|path|string| 是 |none|
|Authorization|header|string| 否 |none|

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|查询成功|None|

# 数据模型

