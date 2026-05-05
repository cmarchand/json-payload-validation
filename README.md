# Validating json request body

Sometime, you have a json schema that defines the structure of your request body.

This schema is part of your API contract, and should be used to validate the data
you process in your rest controllers. This schema may define a lot of constraints,
and if you are able to validate json against these constraints, you'll avoid re-implementing
controls in your DTOs.

But validating Json **before** it has been bind to Java objects is not straightforward.
This projects gives a sample of how to do it with Spring-Boot 4.

It relies on [https://github.com/networknt/json-schema-validator](https://github.com/networknt/json-schema-validator)
to validate Json.

It is based on an old article from [JavaThinking](https://www.javathinking.com/blog/json-schema-validation-in-spring-rest-apis/).