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

    val tcpDumpData = TcpDumpData()
    lines.filter { it.length >= 50 }
            .filter { it[0] == '\t' }
            .forEach({
                tcpDumpData.parseLine(it)
            })
    println(tcpDumpData)
}

