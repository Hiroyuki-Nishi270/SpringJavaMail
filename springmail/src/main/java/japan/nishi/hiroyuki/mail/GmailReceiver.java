package japan.nishi.hiroyuki.mail;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

import japan.nishi.hiroyuki.IExecutableService;
import japan.nishi.hiroyuki.mail.gmail.GmailAuthentication;

public class GmailReceiver implements IExecutableService {

	@Override
	public void execute() {
		// Build a new authorized API client service.

		Gmail service = new GmailAuthentication().getService("Spring Gmail","/credentials.json","tokens");

		List<Message> messageList;


		try {
			messageList = service.users().messages().list("me")
					.setMaxResults(500L).setQ(null).execute().getMessages();

			Message m = messageList.getFirst();

			String messageId = m.getId();

			Message fullMessage = service.users().messages().get("me", messageId).setFormat("full").execute();

			// ペイロードから本文を抽出
			MessagePart payload = fullMessage.getPayload();

			MessagePartBody mpb = payload.getBody();
			
			String str = mpb.getData();
			if (str == null) {
	            // 部分的なメッセージの場合
	            str = payload.getParts().get(0).getBody().getData();
	        }

			System.out.println("---------");
			for(int i = 0 ; i < payload.getParts().size() ; i++) {
				System.out.println(i + " ---------");
				String s = payload.getParts().get(i).getBody().getData();
				System.out.println(new String(Base64.getUrlDecoder().decode(s)));
			}

//			System.out.println(new String(Base64.getUrlDecoder().decode(str)));

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		System.out.println("done");

	}


}
