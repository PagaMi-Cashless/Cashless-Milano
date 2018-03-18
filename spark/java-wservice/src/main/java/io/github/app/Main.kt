package io.github.app

import spark.*;

import graphql.GraphQL
import com.mongodb.util.JSON
import io.github.app.conf.ApiEndpoint
import io.github.app.conf.ConfigKey
import io.github.app.conf.ConfigManager
import io.github.app.handlers.NexiHandlers
import io.github.app.schema.PaymentResponseSchema
import io.github.app.schema.PaymentSchema
import io.github.app.schema.TodoSchema
import io.github.app.utils.JsonUtils

object Main
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        // Initialize the configuration subsystem
        ConfigManager.bootstrap()

        // Configure Spark
        val sparkPort = ConfigManager.getKeyValue(ConfigKey.KEY_SPARK_PORT)
        Spark.port(Integer.parseInt(sparkPort))

        // Configure static file routes
        Spark.staticFileLocation("/static")

        // Enable CORS
        Spark.options("/*") { request, response -> corsHeaderOptions(request, response) }

        Spark.before { _, response -> response.header("Access-Control-Allow-Origin", "*") }

        // Configure GraphQL API on path `/graphql`
        Spark.post("/graphql") {
            request, response -> graphQlSerialize(request, response)
        }

        // Configure Nexi Gateway on path `rest/nexi/pay`
        Spark.post(ApiEndpoint.NEXI_GATEWAY_PAY) { request, response -> run {

                jsonResponse(response)

                try
                {
                    val schema: PaymentSchema = JsonUtils.parse(request.body(), PaymentSchema().javaClass)
                    val res: PaymentResponseSchema = NexiHandlers.handlePayment(schema)
                    JsonUtils.serialize(res)
                }
                catch (ex: Exception) {
                    "{}"
                }
            }
        }

        // Configure Nexi Bundle requests on `rest/nexi/pay/bundle`
        Spark.get(ApiEndpoint.NEXI_GATEWAY_BUNDLE) { request, response -> run {

                htmlResponse(response)

                NexiHandlers.injectBundle(request)
            }
        }
    }

    private fun corsHeaderOptions(request: Request, response: Response): String
    {
        val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
        val accessControlRequestMethod = request.headers("Access-Control-Request-Method")

        accessControlRequestHeaders?.let {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }

        accessControlRequestMethod?.let {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }

        return "OK"
    }

    private fun graphQlSerialize(request: Request, response: Response): String
    {
        // Get the schema instance
        val graphql = GraphQL(TodoSchema.schema)

        // Set the response type
        response.type("application/json")

        // Execute the GraphQL query, serialize and return the packed response
        return JSON.serialize(graphql.execute(request.body()).data)
    }

    private fun jsonResponse(response: Response) = response.type("application/json")
    private fun htmlResponse(response: Response) = response.type("text/html")
}
