# cost_delivery_api

## Instructions
Create an API that will calculate the cost of delivery of a parcel based on weight and volume
(volume = height * width * length). The API should accept the following:
1. Weight (kg)
2. Height (cm)
3. Width (cm)
4. Length (cm)
   
The rules for calculating the cost of delivery are in order of priority:

| Priority | Rule Name     | Condition                    | Cost                 |
|----------|---------------|------------------------------|----------------------|
| 1        | Reject        | Weight > 50 kg               | N/A                  |
| 2        | Heavy Parcel  | Weight > 10 kg               | PHP 20 * Weight (kg) |
| 3        | Small Parcel  | Volume is less than 1500 cm3 | PHP 0.03 * Volume    |
| 4        | Medium Parcel | Volume is less than 2500 cm3 | PHP 0.04 * Volume    |
| 5        | Large Parcel  |                              | PHP 0.05 * Volume    |

As the market tends to fluctuate in terms of pricing, the rules needs to be as flexible as possible.
Your API should also accept a voucher code that can be used to provide discounts to the
customer. To get the discount details of the submitted voucher code, you will need to integrate
with the voucher service maintained by another team. You may check their Voucher API
definition [here](https://app.swaggerhub.com/apis/mynt-iat/mynt-programming-exams/1.1.0). This also includes the details of the mock server that they have provided for your
testing.
