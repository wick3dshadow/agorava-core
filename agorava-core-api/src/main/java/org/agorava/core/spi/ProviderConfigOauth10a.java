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

package org.agorava.core.spi;

import org.agorava.core.api.atinject.ExtractorType;
import org.agorava.core.api.atinject.SignatureType;
import org.agorava.core.api.extractor.StringExtractor;
import org.agorava.core.api.extractor.TokenExtractor;
import org.agorava.core.api.oauth.SignaturePlace;
import org.agorava.core.api.oauth.Token;
import org.agorava.core.api.rest.Verb;
import org.agorava.core.api.service.SignatureService;
import org.agorava.core.api.service.TimestampService;

import javax.inject.Inject;

import static org.agorava.core.api.atinject.ExtractorType.Type.HEADER;
import static org.agorava.core.api.atinject.ExtractorType.Type.OAUTH1_BASE_STRING;
import static org.agorava.core.api.atinject.ExtractorType.Type.TOKEN_STD;
import static org.agorava.core.api.atinject.OAuth.OAuthVersion;
import static org.agorava.core.api.atinject.OAuth.OAuthVersion.ONE;
import static org.agorava.core.api.atinject.SignatureType.Type.HMACSHA1;

/**
 * Default Provider configuration implementing the OAuth protocol, version 1.0a
 * <p/>
 * This class is meant to be extended by concrete implementations of the Provider configuration,
 * providing the endpoints and endpoint-http-verbs.
 * <p/>
 * If your Api adheres to the 1.0a protocol correctly, you just need to extend
 * this class and define the getters for your endpoints.
 * <p/>
 * If your Api does something a bit different, you can override the different
 * extractors or services, in order to fine-tune the process.
 *
 * @author Pablo Fernandez
 * @author Antoine Sabot-Durand
 */
public abstract class ProviderConfigOauth10a extends ProviderConfigOauth {

    @Inject
    @ExtractorType(TOKEN_STD)
    TokenExtractor tokenExtractor;

    @Inject
    @ExtractorType(OAUTH1_BASE_STRING)
    StringExtractor baseStringExtractor;

    @Inject
    @ExtractorType(HEADER)
    StringExtractor headerExtractor;

    @Inject
    @SignatureType(HMACSHA1)
    SignatureService signatureService;

    @Inject
    TimestampService timestampService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenExtractor getAccessTokenExtractor() {
        return tokenExtractor;
    }

    /**
     * Returns the base string extractor.
     *
     * @return base string extractor
     */
    public StringExtractor getBaseStringExtractor() {
        return baseStringExtractor;
    }

    /**
     * Returns the header extractor.
     *
     * @return header extractor
     */
    public StringExtractor getHeaderExtractor() {
        return headerExtractor;
    }

    /**
     * Returns the request token extractor.
     *
     * @return request token extractor
     */
    public TokenExtractor getRequestTokenExtractor() {
        return tokenExtractor;
    }

    /**
     * Returns the signature service.
     *
     * @return signature service
     */
    public SignatureService getSignatureService() {
        return signatureService;
    }

    /**
     * Returns the timestamp service.
     *
     * @return timestamp service
     */
    public TimestampService getTimestampService() {
        return timestampService;
    }

    /**
     * Returns the verb for the access token endpoint (defaults to POST)
     *
     * @return access token endpoint verb
     */
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    /**
     * Returns the verb for the request token endpoint (defaults to POST)
     *
     * @return request token endpoint verb
     */
    public Verb getRequestTokenVerb() {
        return Verb.POST;
    }

    /**
     * Returns the URL that receives the request token requests.
     *
     * @return request token URL
     */
    public abstract String getRequestTokenEndpoint();

    /**
     * Returns the URL that receives the access token requests.
     *
     * @return access token URL
     */
    public abstract String getAccessTokenEndpoint();

    /**
     * Returns the URL where you should redirect your users to authenticate
     * your application.
     *
     * @param requestToken the request token you need to authorize
     * @return the URL where you should redirect your users
     */
    public abstract String getAuthorizationUrl(Token requestToken);

    /**
     * {@inheritDoc}
     */
    @Override
    public SignaturePlace getSignatureType() {
        return SignaturePlace.HEADER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthVersion getOAuthVersion() {
        return ONE;
    }
}