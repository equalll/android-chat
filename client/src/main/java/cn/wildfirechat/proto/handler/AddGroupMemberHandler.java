package cn.wildfirechat.proto.handler;

import com.comsince.github.core.ByteBufferList;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;
import com.comsince.github.push.SubSignal;

import cn.wildfirechat.proto.JavaProtoLogic;
import cn.wildfirechat.proto.ProtoService;

public class AddGroupMemberHandler extends AbstractMessageHandler{

    public AddGroupMemberHandler(ProtoService protoService) {
        super(protoService);
    }

    @Override
    public boolean match(Header header) {
        return Signal.PUB_ACK == header.getSignal() && SubSignal.GAM == header.getSubSignal();
    }

    @Override
    public void processMessage(Header header, ByteBufferList byteBufferList) {
        int errorCode = byteBufferList.get();
        logger.i("AddGroupMemberHandler add member errorCode "+errorCode);
        JavaProtoLogic.IGeneralCallback callback = (JavaProtoLogic.IGeneralCallback) getCallback(header.getMessageId());
        if(errorCode == 0){
            callback.onSuccess();
        } else {
            callback.onFailure(errorCode);
        }
    }
}
