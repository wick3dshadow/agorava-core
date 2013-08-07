/*
 * Copyright 2013 Agorava                                                  
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.agorava.core.api.oauth;

import org.agorava.core.api.rest.RestResponse;
import org.agorava.core.api.rest.RestService;
import org.agorava.core.api.rest.RestVerb;

import java.util.Map;

/**
 * Implementation of this interface is used to manage a generic OAuth Service
 *
 * @author Antoine Sabot-Durand
 */
public interface OAuthService extends RestService {

    /**
     * @return the access token for the OAuth service
     */
    public OAuthToken getAccessToken();

    /**
     * Set the Access Token with for an OAuth access
     *
     * @param token the token to set
     */
    public void setAccessToken(OAuthToken token);

    /**
     * Returns the url to the OAuth service to ask an authorization to access the service.
     *
     * @return the REST URL to use request access
     */
    public String getAuthorizationUrl();

    /**
     * Access to OAuth verifier
     *
     * @return the OAUth verifier
     */
    public String getVerifier();

    /**
     * Used to initialize verifier code returned by OAuth service
     *
     * @param verifierStr
     */
    public void setVerifier(String verifierStr);

    /**
     * Initialize the OAuth access token after the service gave an authorization with the Verifier
     */
    public void initAccessToken();

    /**
     * Send an OAuth request signed without any parameter
     *
     * @param verb a REST verb
     * @param uri  the REST address of the request
     * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
     */
    public RestResponse sendSignedRequest(RestVerb verb, String uri);

    /**
     * Send an OAuth request signed with a list a parameter
     *
     * @param verb   a REST verb
     * @param uri    the REST address of the request
     * @param params a Map of key value parameters to send in the header of the request
     * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
     */
    public RestResponse sendSignedRequest(RestVerb verb, String uri, Map<String, ?> params);

    /**
     * Send an OAuth request signed with a single parameter
     *
     * @param verb  a REST verb
     * @param uri   the REST address of the request
     * @param key   name of the parameter
     * @param value value of the parameter
     * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
     */
    public RestResponse sendSignedRequest(RestVerb verb, String uri, String key, Object value);

    /**
     * Initialize and set an OAuth access token from its public and private keys
     *
     * @param token  public key
     * @param secret secret keys
     */
    public void setAccessToken(String token, String secret);

    /**
     * Send an OAuth request signed with an XML Paylad as content
     *
     * @param verb    the REST verb of the request
     * @param uri     the url of the remote request
     * @param payload the content of the XML payload to send to the service
     * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
     */
    public RestResponse sendSignedXmlRequest(RestVerb verb, String uri, String payload);

    /**
     * @return the session settings of the given service
     */
    public OAuthSession getSession();

    /**
     * Signs and sends a simple request
     *
     * @param request
     * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
     */
    public RestResponse sendSignedRequest(OAuthRequest request);

    /**
     * Perform a conditionally signed REST get command and return an object of the provided class
     *
     * @param uri    a string with {@link java.text.MessageFormat} placeholders (i.e. {0}, {1}) style for params. It's the uri to perform the REST get call
     * @param clazz  class of the returned object
     * @param signed indicate if the request has to be signed or not
     * @param <T>    generic type for returned object
     * @return the answer in the asked class T
     */
    <T> T get(String uri, Class<T> clazz, boolean signed);

    /**
     * Gives the OAuth verifier parameter name
     *
     * @return the verifier name
     */
    String getVerifierParamName();

    /**
     * Close the connexion to the current Oauth service by resetting the OAuthSession
     */
    public void resetSession();
}
