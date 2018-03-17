/**
 *  @author Ayoub Chouak (a.chouak)
 *  @brief  User Schema
 *
 */

package io.github.app.schema

import graphql.GraphQL;
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema

class UserSchema
{
    companion object
    {
        val userType: GraphQLObjectType = GraphQLObjectType.newObject().name("User").build()
        val schema: GraphQLSchema = GraphQLSchema.newSchema().build()
    }
}