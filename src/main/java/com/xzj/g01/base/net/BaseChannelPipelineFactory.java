package com.xzj.g01.base.net;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;

import com.ebo.synframework.nettybase.codec.ProtoDecoder;
import com.ebo.synframework.nettybase.codec.ProtoEncoder;

public class BaseChannelPipelineFactory implements ChannelPipelineFactory{

	private ChannelUpstreamHandler handler;
	
	public BaseChannelPipelineFactory(ChannelUpstreamHandler handler){
		this.handler = handler;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new ProtoDecoder());
		pipeline.addLast("encoder", new ProtoEncoder());
		pipeline.addLast("handler", handler);
		return pipeline;
	}
	
}
