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
    val NEXI_NONCE_PAYMENT = "https://int-ecommerce.nexi.it/ecomm/api/hostedPayments/pagaNonce"

    // GraphQL
    val GRAPHQL_ROOT = "graphql"
    val GQL_ENDPOINT_USER = "$GRAPHQL_ROOT/user"
}