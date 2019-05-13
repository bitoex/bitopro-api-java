# BitoPro API Client for Java

- [BitoPro API Client for Java](#bitopro-api-client-for-java)
  - [Requirement](#requirement)
  - [Installation](#installation)
    - [Limitations](#limitations)
      - [Rate Limit](#rate-limit)
      - [Precisions](#precisions)
      - [Minimum order amount](#minimum-order-amount)
  - [Getting Started](#getting-started)
    - [Initialization](#initialization)
      - [Public API](#public-api)
      - [Authenticated API](#authenticated-api)
        - [Basic](#basic)
        - [From Environment Variable](#from-environment-variable)
        - [From Java System Variable](#from-java-system-variable)
        - [From Properties](#from-properties)
        - [Pair Specific client](#pair-specific-client)
    - [Public REST Endpoints](#public-rest-endpoints)
      - [getOrderBook](#getorderbook)
      - [getTicker](#getticker)
      - [getTickers](#gettickers)
      - [getTrades](#gettrades)
    - [Authenticated REST Endpoints](#authenticated-rest-endpoints)
      - [getAccountBalances](#getaccountbalances)
      - [getOrderHistory](#getorderhistory)
      - [getOrders](#getorders)
      - [createLimitOrder](#createlimitorder)
      - [createMarketOrder](#createmarketorder)
      - [cancelOrder](#cancelorder)
      - [getOrder](#getorder)
  - [Contributing](#contributing)
  - [License](#license)

## Requirement

Requires minimum of Java 8.

## Installation

Java client version scheme {major}.{minor}.{hotfix}.

Java client version will follow the API's major and minor version. Hotfix will be used for Java fixes.

Gradle:

```gradle
compile group: 'com.bitoex', name: 'bitopro-api-java', version: '2.3.0'
```

Maven:

```xml
<dependency>
    <groupId>com.bitoex</groupId>
    <artifactId>bitopro-api-java</artifactId>
    <version>2.3.0</version>
</dependency>
```
### Limitations

#### Rate Limit

There is rate limits applied to each API, please check [API documentation](https://developer.bitopro.com/docs) for more detail.

#### Precisions

Both price and amount are subject to decimal restrictions, please check [official settings](https://www.bitopro.com/fees) for more detail.

#### Minimum order amount

Checkout the [official settings](https://www.bitopro.com/fees) of minimum amount.


## Getting Started

### Initialization

Create a `BitoProClientBuilder`. There are two ways to create a builder.

Use default `HttpClient`:
```java
import com.bitoex.bitorpo.java.client.BitoProClientBuilder;

BitoProClientBuilder builder = BitoProClientBuilder.withDefaultClient();
```

Use your own `HttpClient`:
```java
CloseableHttpClient client = ...;
BitoProClientBuilder builder = BitoProClientBuilder.withClient(client);
```

#### Public API

Methods for public APIs are packaged in `BitoProPublicClient` which can be created through `BitoProClientBuilder`.

```java

BitoProPublicClient publicClient = builder.createPublic();
//convient pair specific client
BitoProPublicPairClient btcTwdPublicClient = publicClient.getPairClient("btc_twd");
```

#### Authenticated API

Methods for authenticated APIs are packaged in `BitoProClient` which can be created in various ways through `BitoProClientBuilder` depends on how you setup your authenticate information. To use the authenticated APIs, you need the following information, **API Key**, **API Secret**, **Account Email**. You can create an API key
[here](https://www.bitopro.com/api). 

##### Basic
```java
BitoProClient client = builder.create(apiKey, secret, email);
```

##### From Environment Variable

```java
 // These are the keys will be looked up in environment variable using System.getenv.
 // BITO_PRO_KEY
 // BITO_PRO_SECRET
 // BITO_PRO_EMAIL
BitoProClient client = builder.createWithEnv();
```

##### From Java System Variable

```java
 // These are the keys will be looked up in Java properties using System.getProperty.
 // BITO_PRO_KEY
 // BITO_PRO_SECRET
 // BITO_PRO_EMAIL
BitoProClient client = builder.createWithSystemProperties();
```

##### From Properties

```java
 // These are the keys will be looked up in the properties provided.
 // bitopro.key
 // bitopro.secret
 // bitopro.email
BitoProClient client = builder.createWithProperties(Properties properties);
```

##### Pair Specific client

```java
BitoProPairClient btcTwdClient = client.getPairClient("btc_twd");
```

### Public REST Endpoints

#### getOrderBook
```java
OrderBook orderbook = publicClient.getOrderBook("btc_twd");
//or
OrderBook orderbook = btcTwdPublicClient.getOrderBook();
```

<details>
<summary>JSON Response</summary>

```js
{
  "bids": [
    {
      "price": "180500",
      "amount": "0.12817687",
      "count": 1,
      "total": "0.12817687"
    },
    {
      "price": "180010",
      "amount": "0.32292",
      "count": 2,
      "total": "0.45109687"
    },
    {
      "price": "180000",
      "amount": "0.24236",
      "count": 3,
      "total": "0.69345687"
    }
  ],
  "asks": [
    {
      "price": "180599",
      "amount": "0.00326056",
      "count": 1,
      "total": "0.00326056"
    },
    {
      "price": "180600",
      "amount": "0.04202575",
      "count": 1,
      "total": "0.04528631"
    }
  ]
}
```
</details>

#### getTicker
```java
Ticker ticker = publicClient.getTicker("btc_twd");
//or
Ticker ticker = btcTwdPublicClient.getTicker();
```
<details>
<summary>JSON Response</summary>

```js
{
  "data": {
    "pair": "xem_eth",
    "lastPrice": "0.00010800",
    "isBuyer": false,
    "priceChange24hr": "0",
    "volume24hr": "0.00000000",
    "high24hr": "0.00010800",
    "low24hr": "0.00010800"
  }
}
```
</details>

#### getTickers
```java
List<Ticker> tickers = publicClient.getTickers();
```
<details>
<summary>JSON Response</summary>

```js
{
  "data": [
    {
      "pair": "xem_btc",
      "lastPrice": "0.00000098",
      "isBuyer": false,
      "priceChange24hr": "0",
      "volume24hr": "0.00000000",
      "high24hr": "0.00000098",
      "low24hr": "0.00000098"
    },
    {
      "pair": "bch_eth",
      "lastPrice": "0.60010000",
      "isBuyer": false,
      "priceChange24hr": "0",
      "volume24hr": "0.00000000",
      "high24hr": "0.60010000",
      "low24hr": "0.60010000"
    },
    {
      "pair": "eth_usdt",
      "lastPrice": "179.22000000",
      "isBuyer": true,
      "priceChange24hr": "10.85",
      "volume24hr": "925.14654180",
      "high24hr": "182.30000000",
      "low24hr": "159.94000000"
    }
  ]
}
```
</details>

#### getTrades
```java
List<Trade> trades = publicClient.getTrades("btc_twd");
//or
List<Trade> trades = btcTwdPublicClient.getTrades();
```

<details>
<summary>JSON Response</summary>

```js
{
  "data": [
    {
      "timestamp": 1557203407,
      "price": "180500.00000000",
      "amount": "0.07717687",
      "isBuyer": false
    },
    {
      "timestamp": 1557203187,
      "price": "180500.00000000",
      "amount": "0.05100000",
      "isBuyer": false
    },
    {
      "timestamp": 1557203053,
      "price": "180500.00000000",
      "amount": "0.01860000",
      "isBuyer": false
    },
    {
      "timestamp": 1557202804,
      "price": "180500.00000000",
      "amount": "0.04781533",
      "isBuyer": false
    },
    {
      "timestamp": 1557202804,
      "price": "180500.00000000",
      "amount": "0.06000000",
      "isBuyer": false
    }
  ]
}
```
</details>

### Authenticated REST Endpoints

#### getAccountBalances

```java
List<Balance> balances = getAccountBalances();
```

<details>
<summary>JSON Response</summary>

```js
{
  "data": [
    {
      "amount": "10001",
      "available": "1.0",
      "currency": "bito",
      "stake": "10000"
    },
    {
      "amount": "0.0",
      "available": "1.0",
      "currency": "btc",
      "stake": "0"
    },
    {
      "amount": "3.0",
      "available": "0.01",
      "currency": "eth",
      "stake": "0"
    },
    {
      "amount": "30000",
      "available": "2500",
      "currency": "twd",
      "stake": "0"
    }
  ]
}
```

</details>

#### getOrderHistory

```java
List<Order> orderHistory = client.getOrderHistory();
```

<details>
<summary>JSON Response</summary>

```js
{
  "data": [
    {
      "action": "buy",
      "avgExecutionPrice": "100000.00000000",
      "bitoFee": "0.00000000",
      "executedAmount": "1.00000000",
      "fee": "0.00100000",
      "feeSymbol": "BTC",
      "id": "123",
      "originalAmount": "1.00000000",
      "pair": "btc_twd",
      "price": "100000.00000000",
      "remainingAmount": "0.00000000",
      "status": 2,
      "timestamp": 1508753757000,
      "type": "limit"
    },
    {
      "action": "buy",
      "avgExecutionPrice": "100000.00000000",
      "bitoFee": "0.00000000",
      "executedAmount": "1.00000000",
      "fee": "0.00200000",
      "feeSymbol": "BTC",
      "id": "456",
      "originalAmount": "1.00000000",
      "pair": "btc_twd",
      "price": "100000.00000000",
      "remainingAmount": "0.00000000",
      "status": 2,
      "timestamp": 1508753787000,
      "type": "limit"
    }
  ]
}
```

</details>

#### getOrders

```java
//pair: btc_twd, include history: false, page: 1
PaginatedList<Order> orders = client.getOrders("btc_twd", false, 1);
//or
PaginatedList<Order> orders = btcTwdClient.getOrders(false, 1);

int currentPage = orders.getPage();
int totalPage = orders.getTotalPages();
for (Order o : orders) {
    System.out.println(o);
}
```

<details>
<summary>JSON Response</summary>

```js
{
  "data": [
    {
      "action": "buy",
      "avgExecutionPrice": "100000.00000000",
      "bitoFee": "0.00000000",
      "executedAmount": "1.00000000",
      "fee": "0.00100000",
      "feeSymbol": "BTC",
      "id": "123",
      "originalAmount": "1.00000000",
      "pair": "btc_twd",
      "price": "100000.00000000",
      "remainingAmount": "0.00000000",
      "status": 2,
      "timestamp": 1508753757000,
      "type": "limit"
    },
    {
      "action": "buy",
      "avgExecutionPrice": "100000.00000000",
      "bitoFee": "0.00000000",
      "executedAmount": "1.00000000",
      "fee": "0.00200000",
      "feeSymbol": "BTC",
      "id": "456",
      "originalAmount": "1.00000000",
      "pair": "btc_twd",
      "price": "100000.00000000",
      "remainingAmount": "0.00000000",
      "status": 2,
      "timestamp": 1508753787000,
      "type": "limit"
    }
  ],
  "page": 1,
  "totalPages": 10
}
```

</details>

#### createLimitOrder

```java
BigDecimal price = new BigDecimal("1.0");
BigDecimal amount = new BigDecimal("0.235");
OrderResponse or = client.createLimitOrder("btc_twd", OrderAction.BUY, price, amount);
//or
OrderResponse or = btcTwdClient.createLimitOrder(OrderAction.BUY, price, amount);
```

<details>
<summary>JSON Response</summary>

```js
{
  "action": "buy",
  "amount": "0.235",
  "orderId": "11233456",
  "price": "1.0",
  "timestamp": 1504262258000
}
```

</details>

#### createMarketOrder

```java

BigDecimal twdAmount = new BigDecimal("0.235");
OrderResponse or = client.createMarketOrder("btc_twd", OrderAction.BUY, amount);
//or
OrderResponse or = btcTwdClient.createMarketOrder(OrderAction.BUY, amount);
```

<details>
<summary>JSON Response</summary>

```js
{
  "action": "buy",
  "amount": "0.235",
  "orderId": "123",
  "timestamp": 1504262258000
}
```

</details>

#### cancelOrder

```java
Optional<OrderResponse> or = client.cancelOrder("btc_twd", "123");
//or
Optional<OrderResponse> or = btcTwdClient.cancelOrder("123");

or.ifPresent(o -> System.out.Println("order " + o.getOrderId() + " cancelled successfully"));
```

<details>
<summary>JSON Response</summary>

```js
{
  "action": "buy",
  "amount": 2.3,
  "orderId": "123",
  "price": 1.2,
  "timestamp": 1504262258000
}
```

</details>

#### getOrder

```java
Optional<Order> order = client.getOrder("btc_twd", "123");
//or
Optional<Order> order = btcTwdClient.getOrder("123");

order.ifPresent(System.out::println);
```

<details>
<summary>JSON Response</summary>

```js
{
  "action": "sell",
  "avgExecutionPrice": "112000.00000000",
  "bitoFee": "103.70370360",
  "executedAmount": "1.00000000",
  "fee": "0.00000000",
  "feeSymbol": "TWD",
  "id": "123",
  "originalAmount": "1.00000000",
  "pair": "btc_twd",
  "price": "112000.00000000",
  "remainingAmount": "0.00000000",
  "status": 2,
  "timestamp": 1508753757000,
  "type": "limit"
}
```

</details>

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/bitoex/bitopro-api-java and this project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct.

1. Fork it
2. Create your feature branch (```git checkout -b my-new-feature```).
3. Commit your changes (```git commit -am 'Added some feature'```)
4. Push to the branch (```git push origin my-new-feature```)
5. Create new Pull Request


## License

The SDK is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).