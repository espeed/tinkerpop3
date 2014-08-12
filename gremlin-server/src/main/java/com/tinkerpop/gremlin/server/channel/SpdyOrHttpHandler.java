package com.tinkerpop.gremlin.server.channel;

import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.internal.StringUtil;

import javax.net.ssl.SSLEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**                                                                                                                    * @author James Thornton (http://jamesthornton.com)                                                                  */

/**
 * Negotiates with the browser if SPDY or HTTP is going to be used. Once decided, the Netty pipeline is setup with
 * the correct handlers for the selected protocol.
 */
public class SpdyOrHttpHandler extends SpdyOrHttpChooser {
    private static final Logger logger = LoggerFactory.getLogger(SpdyOrHttpHandler.class);

    private static final int MAX_CONTENT_LENGTH = 1024 * 100;

    public SpdyOrHttpHandler() {
        this(MAX_CONTENT_LENGTH, MAX_CONTENT_LENGTH);
    }

    public SpdyOrHttpHandler(int maxSpdyContentLength, int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }

    /**
     * Return the {@link SelectedProtocol} for the {@link SSLEngine}. If its not known yet implementations MUST return
     * {@link SelectedProtocol#UNKNOWN}.
     *
     */
    // @Override
    // protected SelectedProtocol getProtocol(SSLEngine engine) {
    //     try {
    //         String[] protocol = StringUtil.split(engine.getSession().getProtocol(), ':');
    //         if (protocol.length < 2) {
    //             // Use HTTP/1.1 as default
    //             return SelectedProtocol.HTTP_1_1;
    //         }
    //         SelectedProtocol selectedProtocol = SelectedProtocol.protocol(protocol[1]);
    //         return selectedProtocol;
    //     } catch (Exception ex) {
    //         logger.warn("SSL SelectedProtocol UNKNOWN");
    //         return SpdyOrHttpChooser.SelectedProtocol.UNKNOWN; 
    //     }
    // }

    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
        return new HttpServerCodec(); // dummy handler
    }

}
