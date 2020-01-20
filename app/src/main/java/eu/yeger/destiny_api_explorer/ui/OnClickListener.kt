package eu.yeger.destiny_api_explorer.ui

class OnClickListener<T>(private val block: (T) -> Unit) {
    fun onClick(input: T) = block(input)
}
