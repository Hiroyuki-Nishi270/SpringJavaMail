package japan.nishi.hiroyuki.mail;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;

import org.springframework.beans.factory.annotation.Autowired;
import japan.nishi.hiroyuki.IExecutableService;

public class MailReceiver implements IExecutableService {

    @Autowired
    MailProperty mailProperty;
    
    @Override
	public void execute() {
        
        Session session = Session.getDefaultInstance(mailProperty, null);
        
        try {
            // ストアへの接続
            Store store = session.getStore(mailProperty.getProtocol());
            store.connect(mailProperty.getUsername(), mailProperty.getPassword());

            // 受信トレイを開く
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // メッセージの取得
            Message[] messages = inbox.getMessages();
            System.out.println("受信したメール数: " + messages.length);

//            for (int i = 0; i < messages.length; i++) {
            for (int i = 0; i < 1; i++) {
                Message message = messages[i];
                System.out.println("------------------------------------");
                System.out.println("件名: " + message.getSubject());
                System.out.println("送信元: " + message.getFrom()[0]);
                System.out.println("送信日時: " + message.getSentDate());

                // メッセージの内容の取得
                Object content = message.getContent();
                if (content instanceof String) {
                    System.out.println("本文: " + (String) content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int j = 0; j < multipart.getCount(); j++) {
                        BodyPart bodyPart = multipart.getBodyPart(j);
                        if (bodyPart.isMimeType("text/plain")) {
                            System.out.println("本文 (プレーンテキスト): " + bodyPart.getContent());
                        } else if (bodyPart.isMimeType("text/html")) {
                            System.out.println("本文 (HTML): " + bodyPart.getContent());
                        }
                        // 添付ファイルの処理などもここに追加できます
                    }
                }
            }

            // クリーンアップ
            inbox.close(false); // falseは変更を保存しないことを意味
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
