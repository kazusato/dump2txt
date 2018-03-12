package kazusato.tools.dump2txt

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main(args: Array<String>) {
    val lines = mutableListOf<String>()
    BufferedReader(InputStreamReader(System.`in`)).use { br ->
        br.lines().forEach( {
            lines.add(it)
        })
    }

    lines.filter { it.length >= 50 }
            .filter { it[0] == '\t' }
            .forEach({
                println(Arrays.toString(toIntArray(it.substring(10, 49))))
            })
}

private fun toIntArray(line: String): IntArray {
    return Arrays.stream(arrayOf<String>(
            line.substring(0, 2),
            line.substring(2, 4),
            line.substring(5, 7),
            line.substring(7, 9),
            line.substring(10, 12),
            line.substring(12, 14),
            line.substring(15, 17),
            line.substring(17, 19),
            line.substring(20, 22),
            line.substring(22, 24),
            line.substring(25, 27),
            line.substring(27, 29),
            line.substring(30, 32),
            line.substring(32, 34),
            line.substring(35, 37),
            line.substring(37, 39)

    )).filter { it != "  " }.mapToInt { it.toInt(16) }.toArray()
}
