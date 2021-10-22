package org.cqfn.save.plugins.fixandwarn

import org.cqfn.save.core.config.TestConfigSections
import org.cqfn.save.core.plugin.PluginConfig
import org.cqfn.save.plugin.warn.WarnPluginConfig
import org.cqfn.save.plugins.fix.FixPluginConfig

import okio.Path
import okio.Path.Companion.toPath

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @property fix config for nested [fix] section
 * @property warn config for nested [warn] section
 */
@Serializable
data class FixAndWarnPluginConfig(
    val fix: FixPluginConfig,
    val warn: WarnPluginConfig
) : PluginConfig {
    override val type = TestConfigSections.`FIX AND WARN`

    @Transient
    override var configLocation: Path = "undefined_toml_location".toPath()

    @Transient
    override var ignoreLinesPatterns: MutableList<Regex> = defaultIgnoreLines

    override fun mergeWith(otherConfig: PluginConfig): PluginConfig {
        val other = otherConfig as FixAndWarnPluginConfig
        val mergedFixPluginConfig = fix.mergeWith(other.fix)
        val mergedWarnPluginConfig = warn.mergeWith(other.warn)
        return FixAndWarnPluginConfig(
            mergedFixPluginConfig as FixPluginConfig,
            mergedWarnPluginConfig as WarnPluginConfig
        ).also {
            it.configLocation = this.configLocation
        }
    }

    override fun validateAndSetDefaults(): PluginConfig {
        require(warn.testName.toRegex().matches(fix.resourceNameTest) &&
                fix.batchSize == warn.batchSize
        ) {
            """
               Test files batch sizes should be identical for [fix] and [warn] plugins and [fix] files should match [warn] regex .
               But found [fix]: {${fix.resourceNameTest}, ${fix.batchSize}},
                         [warn]: {${warn.testName}, ${warn.batchSize}}
           """
        }
        return FixAndWarnPluginConfig(
            fix.validateAndSetDefaults(),
            warn.validateAndSetDefaults()
        ).also {
            it.configLocation = this.configLocation
        }
    }
    companion object {
        internal val defaultIgnoreLines = mutableListOf<Regex>()
    }
}
