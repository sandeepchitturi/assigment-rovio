# PlushMarket

Plush market is a trading place for all the fluffy plush toys.

In the market you can either **sell your plush directly at the current market price** 
or **trade it to another one**.
Each trade can only be done once.

Your goal is to implement functionality, which calculates the 
**optimal plush toy sales strategy**:
- Maximizes the sales price
- Minimizes the number of trades

## Assignment

You need to implement the `PlushMarket` interface to return the optimal strategy.

```java
public interface PlushMarket {
    String calculateStrategy(String offerJSON, String marketJSON);
}
```

The interface uses `JSON` format for both input and response. 
For the exact format of the input and response `JSON`
check the examples below.

## Examples

### Example 1

If the `offerJSON` is

```js
{"plush": "Stella"}
```

and `marketJSON` is

```js
{"plushes": [
    {"plush": "RedBird", "price": 80},
    {"plush": "Stella", "price": 90},
    {"plush": "Pig", "price": 75}
],
"trades": [
    {"take": "RedBird", "give": "Pig"},
    {"take": "Pig", "give": "Stella"}
]}
```

then the response providing the optimal strategy is

```js
{"actions":[
    {"action":"sell","plush":"Stella","price":90}
]}
```

since the sales price of the `Stella` is highest, you cannot improve the price by trading.

### Example 2

If the `offerJSON` is

```js
{"plush": "RedBird"}
```

and the `marketJSON` is the same as above, then the optimal strategy is

```js
{"actions":[
    {"action":"trade","give":"RedBird","take":"Pig"},
    {"action":"trade","give":"Pig","take":"Stella"},
    {"action":"sell","plush":"Stella","price":90}
]}
```

since you can trade your `RedBird` to `Pig` and then trade the `Pig` to `Stella` 
with sales price of 90 instead of
selling it directly at the market price of 80.

### Example 3

If the `offerJSON` is

```js
{"plush": "BlueBird"}
```
and the `marketJSON` is the same as above, then the optimal strategy is

```js
{"actions":[]}
```

since there is no market price nor trades for the `BlueBird` plush.

## Things to remember

- Work on the assignment as if it was production code worth 10M$
- Your answer will be graded on the basis of correctness (it works) and code quality (others can see how it works)
- The solution must be submitted by opening a pull request on the GitHub project repository
- Include tests in your answer
- You are free to use any library from [Maven Central]
- Pay attention to the readability of your code

[Maven Central]: http://search.maven.org/
