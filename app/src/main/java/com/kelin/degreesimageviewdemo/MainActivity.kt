package com.kelin.degreesimageviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val images by lazy {
        listOf(
            "https://image.bitauto.com/autoalbum/files/20191120/592/20191120191603163238289-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/488/2019112019160116166154-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/283/2019112019160216214987-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/106/20191120191600160255201-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/725/20191120191556155611471-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/173/20191120191552155279199-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/511/201911201915541554193197-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/035/2019112019155115512288-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/085/201911201915441544153154-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/069/20191120191544154485203-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/908/201911201915451545193289-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/156/20191120191538153889472-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/865/201911201915351535170154-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/829/20191120191537153751256-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/923/201911201915301530109469-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/707/20191120191529152910197-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/541/201911201915271527170398-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/387/20191120191446144611116-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/746/20191120191526152658113-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/473/201911201915231523181313-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/464/20191120191520152096389-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/444/201911201915221522250388-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/894/20191120191518151824065-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/873/201911201915141514216391-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/878/20191120191512151278113-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/757/20191120191509159256468-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/887/2019112019150715737389-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/225/2019112019150315378153-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/998/20191120191507157145398-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/914/2019112019150215259312-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/113/201911201914571457166469-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/974/201911201914551455152154-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/008/201911201914551455239116-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/519/201911201914501450178388-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/679/20191120191448144894298-750x500-w1.png",
            "https://image.bitauto.com/autoalbum/files/20191120/149/201911201914481448172398-750x500-w1.png",
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        divDegreesImageView.setImageResources(images)
    }
}