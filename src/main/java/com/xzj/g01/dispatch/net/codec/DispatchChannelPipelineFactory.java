package com.xzj.g01.dispatch.net.codec;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;

import com.xzj.game_base.nettybase.codec.LengthFieldBasedFrameDecoder;


public class DispatchChannelPipelineFactory implements ChannelPipelineFactory{

	private ChannelUpstreamHandler handler;
	
	private static final int DEFAULT_MAX_FRAME_LENGTH = 80*1024;

    private static final int DEFAULT_LENGTH_FIELD_LENGTH = 4;
	
	public DispatchChannelPipelineFactory(ChannelUpstreamHandler handler){
		this.handler = handler;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(DEFAULT_MAX_FRAME_LENGTH, 0,
                DEFAULT_LENGTH_FIELD_LENGTH, 0, DEFAULT_LENGTH_FIELD_LENGTH, false, false));
		pipeline.addLast("decoder", new DispatchServerProtoDecoder());
		pipeline.addLast("encoder", new DispatchServerProtoEncoder());
		pipeline.addLast("handler", handler);
		return pipeline;
	}
	
}
