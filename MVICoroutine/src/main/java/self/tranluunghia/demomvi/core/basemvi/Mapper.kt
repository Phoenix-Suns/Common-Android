package self.tranluunghia.demomvi.core.basemvi

interface Mapper<in FROM, out TO> {
    fun map(from: FROM): TO
}