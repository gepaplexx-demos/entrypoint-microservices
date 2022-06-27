package at.gepardec.logging;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.logmanager.MDC;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class UuidPropagationClientHeadersFactory implements ClientHeadersFactory {

    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> returnVal = new MultivaluedHashMap<>();

        returnVal.putAll(clientOutgoingHeaders);
        returnVal.putSingle("X-ORDER-UUID", MDC.get("X-ORDER-UUID"));
        returnVal.putSingle("X-BUSINESS-ID", MDC.get("X-BUSINESS-ID"));

        return returnVal;
    }
}
