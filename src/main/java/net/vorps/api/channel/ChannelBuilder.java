package net.vorps.api.channel;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class ChannelBuilder<E> {

    protected String channel;
    private String subChannel;
    private ArrayList<String> values;

    public ChannelBuilder(){
        this.values = new ArrayList<>();
    }

    public ChannelBuilder setChannel(String channel){
        this.channel = channel;
        return this;
    }

    public ChannelBuilder setSubChannel(String subChannel){
        this.subChannel = subChannel;
        return this;
    }

    public ChannelBuilder addValues(Object ...values){
        this.values.addAll(Arrays.stream(values).map(Object::toString).collect(Collectors.toList()));
        return this;
    }

    protected byte[] build(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(this.subChannel);
        for(String value : this.values) out.writeUTF(value);
        return out.toByteArray();
    }

    public abstract void send(E plugin, UUID uuid);
}
