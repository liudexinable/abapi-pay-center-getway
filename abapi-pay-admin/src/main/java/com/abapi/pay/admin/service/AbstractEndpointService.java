package com.mh.sviwo.gps.tcpser.jt808.service.endpoint;

import com.mh.sviwo.gps.tcpser.connect.Session;
import com.mh.sviwo.gps.tcpser.jt808.dto.Message;
import com.mh.sviwo.gps.tcpser.jt808.dto.base.AbstractBody;
import com.mh.sviwo.gps.tcpser.jt808.dto.base.AbstractMessage;

public interface AbstractEndpointService {

    Message handler(Session session, AbstractMessage<AbstractBody> data);

}
