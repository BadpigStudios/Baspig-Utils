module Baspig.Utils.main {
    requires fabric.entity.events.v1;
    requires fabric.events.interaction.v0;
    requires fabric.item.group.api.v1;
    requires net.fabricmc.loader;
    requires org.apache.logging.log4j.core;
    requires org.jetbrains.annotations;
    requires org.slf4j;

    exports baspig.apis.utils;
}