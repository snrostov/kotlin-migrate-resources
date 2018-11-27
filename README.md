Tool for moving resource files from `src` to seprate `resource` directory.

1. Apply `print_all_source_roots.patch` to Kotlin project.
This will add `!!! path/to/source/root` lines to std on project import in idea
2. Reimport Kotlin project.
3. Copy stdout to `data.txt`
4. Replace `val root = File("/Users/jetbrains/kotlin")` with path to kotlin clone
5. Run main