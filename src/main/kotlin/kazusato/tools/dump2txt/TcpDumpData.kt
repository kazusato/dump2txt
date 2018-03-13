package kazusato.tools.dump2txt

import java.util.*

data class TcpDumpData(
        private val byteList: MutableList<Byte> = mutableListOf()
) {
    companion object {
        const val IP_HEADER_LEN = 14
        const val TCP_HEADER_LEN_POS = 12
    }

    private var positionByte = 0

    fun parseLine(line: String) {
        // remove a line header and a trailing ASCII portion
        byteList.addAll(toByteList(line.substring(10, 49)))
    }

    fun skipEthenetHeader() {
        positionByte += IP_HEADER_LEN
    }

    fun skipIpHeader() {
        val ipFirstByte = byteList[positionByte] as Int
        val ipHeaderWordLen = ipFirstByte shr 4
        val ipHeaderByteLen = ipHeaderWordLen * 4
        positionByte += ipHeaderByteLen
    }

    fun skipTcpHeader() {
        val tcpLenByte = byteList[positionByte + TCP_HEADER_LEN_POS] as Int
        val tcpHeaderWordLen = tcpLenByte shr 4
        val tcpHeaderByteLen = tcpHeaderWordLen * 4
        positionByte += tcpHeaderByteLen
    }

    private fun toByteList(line: String): MutableList<Byte> {
        // data example:
        // 0x01f0:  fe6b b37d da85 b479 9067 4800 4f0b dd3e  .k.}...y.gH.O..>
        // 0x0200:  7f01 68fd 78ab 3a01 0000                 ..h.x.:...
        // This function's argument (line) contains characters between fe6b and dd3e for the first line
        // and between 7f01 and 15 white spaces after 0000 for the second line.
        val byteList = mutableListOf<Byte>()
        Arrays.stream(arrayOf<String>(
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
        )).filter { it != "  " }.forEach {
            try {
                byteList.add(it.toInt(16).toByte())
            } catch (e: Exception) {
                // throw a wrapped exception with the line data
                throw RuntimeException("original line data: ${line}", e)
            }
        }
        return byteList
    }
}