package com.xzj.g01.dispatch.net.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.ebo.synframework.nettybase.codec.ProtoFactory;
import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.google.protobuf.GeneratedMessage;

public class DispatchServerProtoDecoder extends OneToOneDecoder {

	private static Logger logger = Logger.getLogger(DispatchServerProtoDecoder.class);

	@Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer)) {
            return msg;
        }
        ChannelBuffer buf = (ChannelBuffer) msg;
        if (buf.hasArray()) {
        	int length = buf.readableBytes();
        	int serviceAndMethodLength = buf.readByte();
        	ChannelBuffer serviceAndMethodBuffer = buf.readBytes(serviceAndMethodLength);
        	String serviceAndMethodStr = new String(serviceAndMethodBuffer.array());
        	int protoLength = buf.readByte();
        	ChannelBuffer protoBuffer = buf.readBytes(protoLength);
        	String protoNameStr = new String(protoBuffer.array()); 
        	byte[] dst = new byte[buf.readableBytes()];
        	buf.readBytes(dst);
        	GeneratedMessage proto = (GeneratedMessage) ProtoFactory.getProtoInstance(protoNameStr).newBuilderForType().mergeFrom(dst).build();
            ReqCmd cmd = new ReqCmd(0, serviceAndMethodStr, proto, null, length);
        	return cmd;
        } else {
            logger.warn("receive msg meet a null buf");
        }
        return null;
    }

}
