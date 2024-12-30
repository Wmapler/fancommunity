package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.utils.MyActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityDefraud extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvTishi;

    private ImageView left;

    private ImageView right;
    public Integer index=0;

    List<String> titleList = new ArrayList<>();

    List<String> contentList = new ArrayList<>();
    List<String> tishiList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitydefraud);
        back = findViewById(R.id.button_defraud_list_back);
        tvTitle = findViewById(R.id.defraud_title);
        tvContent = findViewById(R.id.defraud_content);
        tvTishi = findViewById(R.id.defraud_tishi);
        left = findViewById(R.id.defraud_left);
        right = findViewById(R.id.defraud_right);

        titleList.add("李七庄街真实案例通报");
        titleList.add("李七庄街真实案例通报");
        titleList.add("李七庄街真实案例通报");

        contentList.add("   2023年5月3日16时许，受害人当时在家中玩手机，看到QQ的一个群里有人发红包，这个人表示谁需要红包可以联系他，之后受害人就加了这个人的好友。对方告诉受害人充值扫码1块多可以得到500多元人民币，受害人就向对方提供二维码扫了1块多，随后对方告诉受害人因为受害人现是未成年人，导致对方账户被冻结，需要解除，如果不解除会报警冻结受害人家里的银行卡，受害人当时就害怕了，就问怎么解除，对方向受害人提供一个二维码，用云闪付扫一下，扫完二维码后输入29980，告诉这是虚拟数字，不是金钱，受害人当时用自己妈妈的手机中云闪付扫了二维码，输入了数字、密码之后发现短信收到一条信息，发现钱被转走了，受害人才知道被骗并进行报警。");
        contentList.add("   2023年5月6日，受害人在家中休息，然后受害人就接到一个电话，对方在电话中自称是物流的客服，说受害人的快递被他们搞丢了，然后要给受害人赔付快递，对方在电话中告诉受害人添加一个QQ群，进到群后，除受害人以外还有其他两个人，随后对方在电话中又告诉受害人一个手机号，让受害人用支付宝搜索这个手机号并向他发起140元的收款，等待对方支付，但是对方称受害人的支付宝因征信原因收款有问题，无法收款，然后对方就在QQ中给受害人发来了一个支付宝的二维码，让受害人扫描来解决这个问题，受害人使用支付宝扫描之后，进入一个聊天框，对方就发消息称受害人的理赔通道没有关闭，如不及时关闭\n" +
                "就会产生额外费用，受害人按照经理的指示在聊天框向对方申请关闭理财通道，之后对方称通过手机银行转账页面输入4980的“虚拟代码”来进行认证，并发给受害人一个理赔中心虚拟认证账户，上面显示一个认证户名和认证账号，并称通过手机银行认证后即可关闭理财通道，受害人打开手机银行的转账页面后，和对方说这是转钱的页面，但对方称这是代码，受害人信以为真，就点了转账，之后受害人输入建设银行发来的验证码，随后钱就给对方转了过去，之后受害人就察觉被骗进行报警，共损失4980元。");
        contentList.add("   2023年5月6日，受害人当时在家中玩抖音时发现有一个使用自己姐姐本人头像的抖音号对自己的抖音号进行关注，然后受害人一看头像确实是自己姐姐的照片，受害人就回关了。在5月7日13时许，这个抖音号主动与受害人联系，对方就跟受害人说自己的微信电话因为受限制都无法正常使用，所以通过抖音与受害人联系，想让受害人帮联系一下航空公司的潘经理，询问是否订好从德国回北京的机票，受害人当时也没多想，就打算帮姐姐这个忙，于是受害人就问对方要联系方式，对方给受害人发了一个潘经理的微信号并与潘经理添加了微信好友，核实是否订好机票，潘经理称已经订好了，需要受害人姐姐付票款，然后受害人就又到抖音上联系对方，对方和受害人说她在国外没办法付票款，希望让受害人先帮垫付机票钱，于是受害人就又到微信上联系潘经理，潘经理让受害人向指定账户转账49600元，受害人转款后找潘经理要机票，但是潘经理跟受害人说其姐姐签证过期了，需要交10万元的保证金才能出票，这时候受害人就通过微信语音联系自己的姐姐，询问是否有回国买票这种情况，其姐姐表示并没有，受害人就意识到被骗了，于是就报警了。");

        tishiList.add("   友情提示：因未成年人防范诈骗意识还很薄弱，其在使用手机时家长一定要做好监督。");
        tishiList.add("   友情提示：快递丢失理赔是犯罪分子常用的诈骗手段，大家接到类似电话时一定要主动与快递公司核实情况，不要轻易相信类似消息。");
        tishiList.add("   友情提示：在网络平台上如有亲戚朋友让您帮忙垫付费用或者借钱，一定要与该人电话或者视频联系，确认情况，在进行转账。");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init(index);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index - 1 + titleList.size()) % titleList.size();
                Log.e("wf", index.toString());
                init(index);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % titleList.size();
                Log.e("wf", index.toString());
                init(index);
            }
        });
    }

    public void init(int i) {
        tvTitle.setText(titleList.get(i));
        tvContent.setText(contentList.get(i));
        tvTishi.setText(tishiList.get(i));
    }
}