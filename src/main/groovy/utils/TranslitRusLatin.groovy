package utils

class TranslitRusLatin {

    def charMap = [((char) 'а') : "a", ((char) 'б'): "b", ((char) 'в'): "v", ((char) 'г'): "g", ((char) 'д'): "d", ((char) 'е'): "e", ((char) 'ё'): "jo",
                   ((char) 'ж'): "zh", ((char) 'з') : "z", ((char) 'и'): "i", ((char) 'й'): "j", ((char) 'к'): "k", ((char) 'л'): "l", ((char) 'м'): "m",
                   ((char) 'н'): "n",  ((char) 'о') : "o", ((char) 'п'): "p", ((char) 'р'): "r", ((char) 'с'): "s", ((char) 'т'): "t", ((char) 'у'): "u",
                   ((char) 'ф'): "f",  ((char) 'х') : "kh",((char) 'ц'): "c", ((char) 'ч'): "ch",((char) 'ш'): "sh",((char) 'щ'): "shh",((char) 'ы'): "y",
                   ((char) 'э'): "e",  ((char) 'ю') : "ju",((char) 'я'): "ja",((char) 'ъ'): "",  ((char) 'ь'): "",
                   ((char) '+') : "-", ((char) '_'): "-", ((char) ')'): "-", ((char) '('): "-", ((char) ','): "-",  ((char) ';') : "-", ((char) '&'): "-",
                   ((char) '=') : "-", ((char) '.'): "-", ((char) ' '): "-", ((char) '/'): "-"]

    public String translit(char ch) {
        ch = Character.toLowerCase(ch)
        if(charMap[ch]!=null)
            return charMap[ch];
        if(( (ch >= (char)'A') && (ch <= (char)'Z')) || ((ch >= (char)'a') && (ch <= (char)'z'))||Character.isDigit(ch))
            return String.valueOf(ch)
        else
            return ""
    }

    public String translitText(String text) {

        def result = text==null ? null :
                text.toCharArray()
                        .collect{translit(it)}
                        .join("");
        return result
    }
}
