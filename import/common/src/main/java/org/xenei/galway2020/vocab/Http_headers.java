/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/import/common/src/main/resources/vocabs/http-headers.rdf 
 * @author Auto-generated by schemagen on 13 Jul 2015 18:21 
 */
public class Http_headers {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/2011/http#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Resource a_im = m_model.createResource( "http://www.w3.org/2011/http-headers#a-im" );
    
    public static final Resource accept = m_model.createResource( "http://www.w3.org/2011/http-headers#accept" );
    
    public static final Resource accept_additions = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-additions" );
    
    public static final Resource accept_charset = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-charset" );
    
    public static final Resource accept_encoding = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-encoding" );
    
    public static final Resource accept_features = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-features" );
    
    public static final Resource accept_language = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-language" );
    
    public static final Resource accept_ranges = m_model.createResource( "http://www.w3.org/2011/http-headers#accept-ranges" );
    
    public static final Resource access_control = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control" );
    
    public static final Resource access_control_allow_credentials = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-allow-credentials" );
    
    public static final Resource access_control_allow_headers = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-allow-headers" );
    
    public static final Resource access_control_allow_methods = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-allow-methods" );
    
    public static final Resource access_control_allow_origin = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-allow-origin" );
    
    public static final Resource access_control_max_age = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-max-age" );
    
    public static final Resource access_control_request_headers = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-request-headers" );
    
    public static final Resource access_control_request_method = m_model.createResource( "http://www.w3.org/2011/http-headers#access-control-request-method" );
    
    public static final Resource age = m_model.createResource( "http://www.w3.org/2011/http-headers#age" );
    
    public static final Resource allow = m_model.createResource( "http://www.w3.org/2011/http-headers#allow" );
    
    public static final Resource alternates = m_model.createResource( "http://www.w3.org/2011/http-headers#alternates" );
    
    public static final Resource apply_to_redirect_ref = m_model.createResource( "http://www.w3.org/2011/http-headers#apply-to-redirect-ref" );
    
    public static final Resource authentication_info = m_model.createResource( "http://www.w3.org/2011/http-headers#authentication-info" );
    
    public static final Resource authorization = m_model.createResource( "http://www.w3.org/2011/http-headers#authorization" );
    
    public static final Resource c_ext = m_model.createResource( "http://www.w3.org/2011/http-headers#c-ext" );
    
    public static final Resource c_man = m_model.createResource( "http://www.w3.org/2011/http-headers#c-man" );
    
    public static final Resource c_opt = m_model.createResource( "http://www.w3.org/2011/http-headers#c-opt" );
    
    public static final Resource c_pep = m_model.createResource( "http://www.w3.org/2011/http-headers#c-pep" );
    
    public static final Resource c_pep_info = m_model.createResource( "http://www.w3.org/2011/http-headers#c-pep-info" );
    
    public static final Resource cache_control = m_model.createResource( "http://www.w3.org/2011/http-headers#cache-control" );
    
    public static final Resource compliance = m_model.createResource( "http://www.w3.org/2011/http-headers#compliance" );
    
    public static final Resource connection = m_model.createResource( "http://www.w3.org/2011/http-headers#connection" );
    
    public static final Resource content_base = m_model.createResource( "http://www.w3.org/2011/http-headers#content-base" );
    
    public static final Resource content_disposition = m_model.createResource( "http://www.w3.org/2011/http-headers#content-disposition" );
    
    public static final Resource content_encoding = m_model.createResource( "http://www.w3.org/2011/http-headers#content-encoding" );
    
    public static final Resource content_id = m_model.createResource( "http://www.w3.org/2011/http-headers#content-id" );
    
    public static final Resource content_language = m_model.createResource( "http://www.w3.org/2011/http-headers#content-language" );
    
    public static final Resource content_length = m_model.createResource( "http://www.w3.org/2011/http-headers#content-length" );
    
    public static final Resource content_location = m_model.createResource( "http://www.w3.org/2011/http-headers#content-location" );
    
    public static final Resource content_md5 = m_model.createResource( "http://www.w3.org/2011/http-headers#content-md5" );
    
    public static final Resource content_range = m_model.createResource( "http://www.w3.org/2011/http-headers#content-range" );
    
    public static final Resource content_script_type = m_model.createResource( "http://www.w3.org/2011/http-headers#content-script-type" );
    
    public static final Resource content_style_type = m_model.createResource( "http://www.w3.org/2011/http-headers#content-style-type" );
    
    public static final Resource content_transfer_encoding = m_model.createResource( "http://www.w3.org/2011/http-headers#content-transfer-encoding" );
    
    public static final Resource content_type = m_model.createResource( "http://www.w3.org/2011/http-headers#content-type" );
    
    public static final Resource content_version = m_model.createResource( "http://www.w3.org/2011/http-headers#content-version" );
    
    public static final Resource cookie = m_model.createResource( "http://www.w3.org/2011/http-headers#cookie" );
    
    public static final Resource cookie2 = m_model.createResource( "http://www.w3.org/2011/http-headers#cookie2" );
    
    public static final Resource cost = m_model.createResource( "http://www.w3.org/2011/http-headers#cost" );
    
    public static final Resource dasl = m_model.createResource( "http://www.w3.org/2011/http-headers#dasl" );
    
    public static final Resource date = m_model.createResource( "http://www.w3.org/2011/http-headers#date" );
    
    public static final Resource dav = m_model.createResource( "http://www.w3.org/2011/http-headers#dav" );
    
    public static final Resource default_style = m_model.createResource( "http://www.w3.org/2011/http-headers#default-style" );
    
    public static final Resource delta_base = m_model.createResource( "http://www.w3.org/2011/http-headers#delta-base" );
    
    public static final Resource depth = m_model.createResource( "http://www.w3.org/2011/http-headers#depth" );
    
    public static final Resource derived_from = m_model.createResource( "http://www.w3.org/2011/http-headers#derived-from" );
    
    public static final Resource destination = m_model.createResource( "http://www.w3.org/2011/http-headers#destination" );
    
    public static final Resource differential_id = m_model.createResource( "http://www.w3.org/2011/http-headers#differential-id" );
    
    public static final Resource digest = m_model.createResource( "http://www.w3.org/2011/http-headers#digest" );
    
    public static final Resource etag = m_model.createResource( "http://www.w3.org/2011/http-headers#etag" );
    
    public static final Resource expect = m_model.createResource( "http://www.w3.org/2011/http-headers#expect" );
    
    public static final Resource expires = m_model.createResource( "http://www.w3.org/2011/http-headers#expires" );
    
    public static final Resource ext = m_model.createResource( "http://www.w3.org/2011/http-headers#ext" );
    
    public static final Resource from = m_model.createResource( "http://www.w3.org/2011/http-headers#from" );
    
    public static final Resource getprofile = m_model.createResource( "http://www.w3.org/2011/http-headers#getprofile" );
    
    public static final Resource host = m_model.createResource( "http://www.w3.org/2011/http-headers#host" );
    
    public static final Resource if_ = m_model.createResource( "http://www.w3.org/2011/http-headers#if" );
    
    public static final Resource if_match = m_model.createResource( "http://www.w3.org/2011/http-headers#if-match" );
    
    public static final Resource if_modified_since = m_model.createResource( "http://www.w3.org/2011/http-headers#if-modified-since" );
    
    public static final Resource if_none_match = m_model.createResource( "http://www.w3.org/2011/http-headers#if-none-match" );
    
    public static final Resource if_range = m_model.createResource( "http://www.w3.org/2011/http-headers#if-range" );
    
    public static final Resource if_unmodified_since = m_model.createResource( "http://www.w3.org/2011/http-headers#if-unmodified-since" );
    
    public static final Resource im = m_model.createResource( "http://www.w3.org/2011/http-headers#im" );
    
    public static final Resource keep_alive = m_model.createResource( "http://www.w3.org/2011/http-headers#keep-alive" );
    
    public static final Resource label = m_model.createResource( "http://www.w3.org/2011/http-headers#label" );
    
    public static final Resource last_modified = m_model.createResource( "http://www.w3.org/2011/http-headers#last-modified" );
    
    public static final Resource link = m_model.createResource( "http://www.w3.org/2011/http-headers#link" );
    
    public static final Resource location = m_model.createResource( "http://www.w3.org/2011/http-headers#location" );
    
    public static final Resource lock_token = m_model.createResource( "http://www.w3.org/2011/http-headers#lock-token" );
    
    public static final Resource man = m_model.createResource( "http://www.w3.org/2011/http-headers#man" );
    
    public static final Resource max_forwards = m_model.createResource( "http://www.w3.org/2011/http-headers#max-forwards" );
    
    public static final Resource message_id = m_model.createResource( "http://www.w3.org/2011/http-headers#message-id" );
    
    public static final Resource meter = m_model.createResource( "http://www.w3.org/2011/http-headers#meter" );
    
    public static final Resource method_check = m_model.createResource( "http://www.w3.org/2011/http-headers#method-check" );
    
    public static final Resource method_check_expires = m_model.createResource( "http://www.w3.org/2011/http-headers#method-check-expires" );
    
    public static final Resource mime_version = m_model.createResource( "http://www.w3.org/2011/http-headers#mime-version" );
    
    public static final Resource negotiate = m_model.createResource( "http://www.w3.org/2011/http-headers#negotiate" );
    
    public static final Resource non_compliance = m_model.createResource( "http://www.w3.org/2011/http-headers#non-compliance" );
    
    public static final Resource opt = m_model.createResource( "http://www.w3.org/2011/http-headers#opt" );
    
    public static final Resource optional = m_model.createResource( "http://www.w3.org/2011/http-headers#optional" );
    
    public static final Resource ordering_type = m_model.createResource( "http://www.w3.org/2011/http-headers#ordering-type" );
    
    public static final Resource origin = m_model.createResource( "http://www.w3.org/2011/http-headers#origin" );
    
    public static final Resource overwrite = m_model.createResource( "http://www.w3.org/2011/http-headers#overwrite" );
    
    public static final Resource p3p = m_model.createResource( "http://www.w3.org/2011/http-headers#p3p" );
    
    public static final Resource pep = m_model.createResource( "http://www.w3.org/2011/http-headers#pep" );
    
    public static final Resource pep_info = m_model.createResource( "http://www.w3.org/2011/http-headers#pep-info" );
    
    public static final Resource pics_label = m_model.createResource( "http://www.w3.org/2011/http-headers#pics-label" );
    
    public static final Resource position = m_model.createResource( "http://www.w3.org/2011/http-headers#position" );
    
    public static final Resource pragma = m_model.createResource( "http://www.w3.org/2011/http-headers#pragma" );
    
    public static final Resource profileobject = m_model.createResource( "http://www.w3.org/2011/http-headers#profileobject" );
    
    public static final Resource protocol = m_model.createResource( "http://www.w3.org/2011/http-headers#protocol" );
    
    public static final Resource protocol_info = m_model.createResource( "http://www.w3.org/2011/http-headers#protocol-info" );
    
    public static final Resource protocol_query = m_model.createResource( "http://www.w3.org/2011/http-headers#protocol-query" );
    
    public static final Resource protocol_request = m_model.createResource( "http://www.w3.org/2011/http-headers#protocol-request" );
    
    public static final Resource proxy_authenticate = m_model.createResource( "http://www.w3.org/2011/http-headers#proxy-authenticate" );
    
    public static final Resource proxy_authentication_info = m_model.createResource( "http://www.w3.org/2011/http-headers#proxy-authentication-info" );
    
    public static final Resource proxy_authorization = m_model.createResource( "http://www.w3.org/2011/http-headers#proxy-authorization" );
    
    public static final Resource proxy_features = m_model.createResource( "http://www.w3.org/2011/http-headers#proxy-features" );
    
    public static final Resource proxy_instruction = m_model.createResource( "http://www.w3.org/2011/http-headers#proxy-instruction" );
    
    public static final Resource public_ = m_model.createResource( "http://www.w3.org/2011/http-headers#public" );
    
    public static final Resource range = m_model.createResource( "http://www.w3.org/2011/http-headers#range" );
    
    public static final Resource redirect_ref = m_model.createResource( "http://www.w3.org/2011/http-headers#redirect-ref" );
    
    public static final Resource referer = m_model.createResource( "http://www.w3.org/2011/http-headers#referer" );
    
    public static final Resource referer_root = m_model.createResource( "http://www.w3.org/2011/http-headers#referer-root" );
    
    public static final Resource resolution_hint = m_model.createResource( "http://www.w3.org/2011/http-headers#resolution-hint" );
    
    public static final Resource resolver_location = m_model.createResource( "http://www.w3.org/2011/http-headers#resolver-location" );
    
    public static final Resource retry_after = m_model.createResource( "http://www.w3.org/2011/http-headers#retry-after" );
    
    public static final Resource safe = m_model.createResource( "http://www.w3.org/2011/http-headers#safe" );
    
    public static final Resource security_scheme = m_model.createResource( "http://www.w3.org/2011/http-headers#security-scheme" );
    
    public static final Resource server = m_model.createResource( "http://www.w3.org/2011/http-headers#server" );
    
    public static final Resource set_cookie = m_model.createResource( "http://www.w3.org/2011/http-headers#set-cookie" );
    
    public static final Resource set_cookie2 = m_model.createResource( "http://www.w3.org/2011/http-headers#set-cookie2" );
    
    public static final Resource setprofile = m_model.createResource( "http://www.w3.org/2011/http-headers#setprofile" );
    
    public static final Resource slug = m_model.createResource( "http://www.w3.org/2011/http-headers#slug" );
    
    public static final Resource soapaction = m_model.createResource( "http://www.w3.org/2011/http-headers#soapaction" );
    
    public static final Resource status_uri = m_model.createResource( "http://www.w3.org/2011/http-headers#status-uri" );
    
    public static final Resource subok = m_model.createResource( "http://www.w3.org/2011/http-headers#subok" );
    
    public static final Resource subst = m_model.createResource( "http://www.w3.org/2011/http-headers#subst" );
    
    public static final Resource surrogate_capability = m_model.createResource( "http://www.w3.org/2011/http-headers#surrogate-capability" );
    
    public static final Resource surrogate_control = m_model.createResource( "http://www.w3.org/2011/http-headers#surrogate-control" );
    
    public static final Resource tcn = m_model.createResource( "http://www.w3.org/2011/http-headers#tcn" );
    
    public static final Resource te = m_model.createResource( "http://www.w3.org/2011/http-headers#te" );
    
    public static final Resource timeout = m_model.createResource( "http://www.w3.org/2011/http-headers#timeout" );
    
    public static final Resource title = m_model.createResource( "http://www.w3.org/2011/http-headers#title" );
    
    public static final Resource trailer = m_model.createResource( "http://www.w3.org/2011/http-headers#trailer" );
    
    public static final Resource transfer_encoding = m_model.createResource( "http://www.w3.org/2011/http-headers#transfer-encoding" );
    
    public static final Resource ua_color = m_model.createResource( "http://www.w3.org/2011/http-headers#ua-color" );
    
    public static final Resource ua_media = m_model.createResource( "http://www.w3.org/2011/http-headers#ua-media" );
    
    public static final Resource ua_pixels = m_model.createResource( "http://www.w3.org/2011/http-headers#ua-pixels" );
    
    public static final Resource ua_resolution = m_model.createResource( "http://www.w3.org/2011/http-headers#ua-resolution" );
    
    public static final Resource ua_windowpixels = m_model.createResource( "http://www.w3.org/2011/http-headers#ua-windowpixels" );
    
    public static final Resource upgrade = m_model.createResource( "http://www.w3.org/2011/http-headers#upgrade" );
    
    public static final Resource uri = m_model.createResource( "http://www.w3.org/2011/http-headers#uri" );
    
    public static final Resource user_agent = m_model.createResource( "http://www.w3.org/2011/http-headers#user-agent" );
    
    public static final Resource variant_vary = m_model.createResource( "http://www.w3.org/2011/http-headers#variant-vary" );
    
    public static final Resource vary = m_model.createResource( "http://www.w3.org/2011/http-headers#vary" );
    
    public static final Resource version = m_model.createResource( "http://www.w3.org/2011/http-headers#version" );
    
    public static final Resource via = m_model.createResource( "http://www.w3.org/2011/http-headers#via" );
    
    public static final Resource want_digest = m_model.createResource( "http://www.w3.org/2011/http-headers#want-digest" );
    
    public static final Resource warning = m_model.createResource( "http://www.w3.org/2011/http-headers#warning" );
    
    public static final Resource www_authenticate = m_model.createResource( "http://www.w3.org/2011/http-headers#www-authenticate" );
    
    public static final Resource x_device_accept = m_model.createResource( "http://www.w3.org/2011/http-headers#x-device-accept" );
    
    public static final Resource x_device_accept_charset = m_model.createResource( "http://www.w3.org/2011/http-headers#x-device-accept-charset" );
    
    public static final Resource x_device_accept_encoding = m_model.createResource( "http://www.w3.org/2011/http-headers#x-device-accept-encoding" );
    
    public static final Resource x_device_accept_language = m_model.createResource( "http://www.w3.org/2011/http-headers#x-device-accept-language" );
    
    public static final Resource x_device_user_agent = m_model.createResource( "http://www.w3.org/2011/http-headers#x-device-user-agent" );
    
}
