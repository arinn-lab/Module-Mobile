package tugasCrud

class AnimalManager {
    private val animals = mutableListOf<Animal>()

    val allAnimals: List<Animal>
        get() = animals.toList()

    fun add(code: String, name: String, species: String): Boolean {
        if (code.isBlank() || name.isBlank() || species.isBlank()) return false
        if (animals.any { it.code == code }) return false

        animals.add(Animal(code, name, species))
        return true
    }

    fun getAll(): List<Animal> = allAnimals

    fun update(code: String, name: String, species: String): Boolean {
        val animal = animals.find { it.code == code } ?: return false
        animal.name = name
        animal.species = species
        return true
    }

    fun delete(code: String): Boolean {
        val animal = animals.find { it.code == code } ?: return false
        animals.remove(animal)
        return true
    }

    fun findByCode(code: String): Animal? = animals.find { it.code == code }
}