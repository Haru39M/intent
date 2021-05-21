package app.wakayama.harusame.intent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val readRequestCode:Int = 42
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentButton.setOnClickListener{
            //Intentクラスからインスタンスを作る(実体化)？
            val toSecondActivityIntent = Intent(this,SecondActivity::class.java)
            startActivity(toSecondActivityIntent)
        }
        playStoreButton.setOnClickListener{
            val playStoreIntent = Intent(Intent.ACTION_VIEW)
            //遷移先に渡すデータ
            playStoreIntent.data = Uri.parse("https://play.google.com/store/apps")
            //Intent型変数.setPackage("アプリケーションID")
            playStoreIntent.setPackage("com.android.vending")
            startActivity(playStoreIntent)
        }
        mapButton.setOnClickListener{
            val mapIntent = Intent(Intent.ACTION_VIEW)
            //遷移先に渡すデータ(緯度経度)
            mapIntent.data = Uri.parse("geo:35.6473,139.7360")
            if(mapIntent.resolveActivity(packageManager) != null){//遷移先アプリがインストールされているかを判定して、されていれば遷移
                startActivity(mapIntent)
            }
        }
        browserButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW)
            //遷移先に渡すデータ(URL)
            browserIntent.data = Uri.parse("https://life-is-tech.com/")
            if(browserIntent.resolveActivity(packageManager) != null){
                startActivity(browserIntent)
            }
        }
        gallelyButton.setOnClickListener {
            //別のアプリが管理しているファイルを取得
            val gallelyIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            gallelyIntent.addCategory(Intent.CATEGORY_OPENABLE)
            //選択できるタイプを画像に限定
            gallelyIntent.type = "image/*"
            //遷移先のアクティビティから結果を受け取る
            //遷移先から戻ってきたときにどこから戻ってきたのかをreadRequestCodeで判別するためにインテントを実行するときに渡している
            startActivityForResult(gallelyIntent,readRequestCode)
        }
    }
    /*遷移先のアクティビティから戻ってくると実行される関数.引数は結果.
    requestCode:インテント実行時に渡した値
    resultCode:アクティビティが正常に実行されたかどうかのデータ
    resultData:帰ってきたデータ
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        //インテント実行時に渡した値が同じかどうかを比較、アクティビティが正しく実行されたか比較
        if(requestCode == readRequestCode && requestCode == Activity.RESULT_OK){
            resultData?.data?.also {uri->
                imageView.setImageURI(uri)
            }
        }
    }
}