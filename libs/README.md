# Local compile-only dependencies

This folder is deliberately empty in git: the jars it holds belong to other projects and
are not ours to redistribute. `neoforge/build.gradle` picks up every jar found here as a
`compileOnly` dependency, so drop in the versions matching `gradle.properties` before
building:

- Applied Energistics 2, matching `ae2_version_range`
- Polymorph+, matching the `polymorph_plus` dependency

Both are on Modrinth and CurseForge.
