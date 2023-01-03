package com.example.gggcapplication

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador("Gandhy", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Gabriel", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Alejandra", "c@c.com")
                )
        }
    }
}
