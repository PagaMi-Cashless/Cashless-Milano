/**
 * @author Ayoub Chouak (a.chouak)
 * @brief  API Endpoints
 */
package io.github.app.conf

object ApiEndpoint
{
    // REST
    val REST_ROOT = "rest"
    val NEXI_GATEWAY_PAY = "$REST_ROOT/nexi/pay"
    val NEXI_GATEWAY_BUNDLE = "$REST_ROOT/nexi/pay/bundle"

    // Nexi API URLs
    val NEXI_NONCE_PAYMENT = "https://int-ecommerce.nexi.it/ecomm/api/hostedPayments/pagaNonce"
    val NEXI_JS_BUNDLE = "https://int-ecommerce.nexi.it/ecomm/hostedPayments/JavaScript"

    // GraphQL
    val GRAPHQL_ROOT = "graphql"
    val GQL_ENDPOINT_USER = "$GRAPHQL_ROOT/user"
}