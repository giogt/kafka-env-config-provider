package org.ggt.kafka.config.provider;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.Is.is;


public class MapBuilderTest {

    @Test
    public void mapBuilder_withNoEntries_mustBuildEmptyMap() {
        MapBuilder<String, String> mapBuilder = new MapBuilder<>();
        Map<String, String> res = mapBuilder.build();
        assertThat(res, is(aMapWithSize(0)));
    }

    @Test
    public void mapBuilder_addEntry_mustAddEntries() {
        MapBuilder<String, String> mapBuilder = new MapBuilder<>();
        Map<String, String> res = mapBuilder
                .addEntry("foo", "fooValue")
                .addEntry("bar", "barValue")
                .build();
        assertThat(res, is(aMapWithSize(2)));
        assertThat(res.get("foo"), is("fooValue"));
        assertThat(res.get("bar"), is("barValue"));
    }

    @Test
    public void mapBuilder_addEntries_forEmptyMap_mustBuildEmptyMap() {
        MapBuilder<String, String> mapBuilder = new MapBuilder<>();
        Map<String, String> res = mapBuilder
                .addEntries(new HashMap<>())
                .build();
        assertThat(res, is(aMapWithSize(0)));
    }

    @Test
    public void mapBuilder_addEntries_forNonEmptyMap_mustBuildMapWithSameEntries() {
        MapBuilder<String, String> mapBuilder = new MapBuilder<>();
        HashMap<String, String> entries = new HashMap<>();
        entries.put("foo", "fooValue");
        entries.put("bar", "barValue");
        Map<String, String> res = mapBuilder
                .addEntries(entries)
                .build();
        assertThat(res, is(aMapWithSize(2)));
        assertThat(res.get("foo"), is("fooValue"));
        assertThat(res.get("bar"), is("barValue"));
    }
}
