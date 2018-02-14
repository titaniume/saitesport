package cn.jack.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jack.core.service.SearchService;


/**
 * 消息处理类
 * @author titaniume
 *
 */
public class CustomMessageListener implements MessageListener{

	
	@Autowired
	private SearchService searchService;
	
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage am = (ActiveMQTextMessage)message;
		try {
		//System.out.println("ActiveMQ中的消息:"+am.getText());
		//保存商品信息到solr服务器
		searchService.insertProductToSolr(Long.parseLong(am.getText()));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
