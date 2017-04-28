package utils

import org.junit.Assert
import org.junit.Test


class TranslitRusLatinTest {
    @Test
    public void testTranslit() {
        TranslitRusLatin translitRusLatin = new TranslitRusLatin()
        Assert.assertEquals(null, translitRusLatin.translitText(null))
        Assert.assertEquals("tizin-ksilo-12-apteka", translitRusLatin.translitText("Tizin Ksilo 12% аптека!@#%‰±¥©®ŽÆÇÐÑ"))
        Assert.assertEquals("alchevsk", translitRusLatin.translitText("алчевск"))
        Assert.assertEquals("alchevsk", translitRusLatin.translitText("АлчеВск"))
        Assert.assertEquals("alchev1sk2", translitRusLatin.translitText("АлЧЕв1ск2"))
        Assert.assertEquals("alchev1sk2ajaju", translitRusLatin.translitText("Алчев1ск2аяю"))
        Assert.assertEquals("alchev1sk2ajaju", translitRusLatin.translitText("Алчев1скь2аЬЪяю"))
        Assert.assertEquals("alchev1sk2ajaju", translitRusLatin.translitText("Алчев1скь2аъяю№!@#”?"))
        Assert.assertEquals("alchev1sk2ajaju---------jo", translitRusLatin.translitText("Алчев1скь2аъяю _)(,;&=.ё"))

    }
}
