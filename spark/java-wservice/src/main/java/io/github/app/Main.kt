package io.github.app

import spark.Spark
import spark.*;

import graphql.GraphQL
import com.mongodb.util.JSON
import io.github.app.conf.ConfigKey
import io.github.app.conf.ConfigManager
import io.github.app.schema.TodoSchema

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

        /*
         * enable CORS in our Spark server. CORS is the acronym for “Cross-origin resource sharing”: a mechanism that allows to access REST resources outside the original domain of the request.
         * http://www.mastertheboss.com/cool-stuff/create-a-rest-services-layer-with-spark
         */
        Spark.options("/*") { request, response -> corsHeaderOptions(request, response) }

        Spark.before { request, response -> response.header("Access-Control-Allow-Origin", "*") }

        // Configure GraphQL API on path `/graphql`
        Spark.post("/graphql") { request, response ->

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
}
