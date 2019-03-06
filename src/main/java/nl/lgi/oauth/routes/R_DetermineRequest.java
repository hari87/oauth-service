package nl.lgi.oauth.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class R_DetermineRequest extends RouteBuilder {
    /**
     * <b>Called on initialization to build the routes using the fluent builder syntax.</b>
     * <p/>
     * This is a central method for RouteBuilder implementations to implement
     * the routes using the Java fluent builder syntax.
     *
     * @throws Exception can be thrown during configuration
     */
    @Override
    public void configure() throws Exception {

        getContext().setStreamCaching(true);

        from("direct:processServiceCruizerRequest")
                .setProperty("temp_body").body()
                .removeHeaders("CamelHttp*")
                .to("http://localhost:8080/sc/auth/fetch/accessToken")
                .log("token is ${body}")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
                .setHeader("Authorization", simple("Bearer ${body}"))
                .setBody().property("temp_body")
                .to("https://qa-api.nextservicecruiser.com/api/v2.4/appointments/proposals")

                .choice()
                    .when().simple("${header.CamelResponseCode} == 401 && ${header.AuthRetry} == null")
                      .setHeader("AuthRetry", constant(1))
                      .to("http://localhost:8080/sc/auth/update/accessToken")
                      .to("direct:processServiceCruizerRequest")
                    .when().simple("${header.CamelResponseCode} == 401 && ${header.AuthRetry} == 1")
                      .setHeader("AuthRetry", constant(2))
                      .to("http://localhost:8080/sc/auth/update/accessToken")
                      .to("direct:processServiceCruizerRequest")
                    .when(header("AuthRetry").isGreaterThan(2))
                        .log("unknown exception occured with authentication")
                        .throwException(java.lang.Exception.class, "unknwn exception occured.")
                    .end()

                .log("${body}");
    }
}
