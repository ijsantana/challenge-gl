import spock.lang.Specification

class UserValidationTest extends Specification {             // 1

    void 'invertir una cadena de texto'() {            // 2
        given: 'una cadena de text'                    // 3
        def miCadena = 'Hola Genbetadev'

        when: 'la invertimos'                          // 4
        def cadenaInvertida = miCadena.reverse()

        then: 'se invierte correctamente'              // 5
        cadenaInvertida == 'vedatebneG aloH'
    }
}