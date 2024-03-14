package com.tencent.supersonic.headless.core.chat.knowledge.builder;

import com.google.common.collect.Lists;
import com.tencent.supersonic.common.pojo.enums.DictWordType;
import com.tencent.supersonic.headless.api.pojo.SchemaElement;
import com.tencent.supersonic.headless.core.chat.knowledge.DictWord;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TagWordBuilder extends BaseWordWithAliasBuilder {

    @Override
    public List<DictWord> doGet(String word, SchemaElement schemaElement) {
        List<DictWord> result = Lists.newArrayList();
        result.add(getOneWordNature(word, schemaElement, false));
        result.addAll(getOneWordNatureAlias(schemaElement, false));
        String reverseWord = StringUtils.reverse(word);
        if (!word.equalsIgnoreCase(reverseWord)) {
            result.add(getOneWordNature(reverseWord, schemaElement, true));
        }
        return result;
    }

    public DictWord getOneWordNature(String word, SchemaElement schemaElement, boolean isSuffix) {
        DictWord dictWord = new DictWord();
        dictWord.setWord(word);
        Long modelId = schemaElement.getModel();
        String nature = DictWordType.NATURE_SPILT + modelId + DictWordType.NATURE_SPILT + schemaElement.getId()
                + DictWordType.TAG.getTypeWithSpilt();
        if (isSuffix) {
            nature = DictWordType.NATURE_SPILT + modelId + DictWordType.NATURE_SPILT + schemaElement.getId()
                    + DictWordType.SUFFIX.getTypeWithSpilt() + DictWordType.TAG.getTypeWithSpilt();
        }
        dictWord.setNatureWithFrequency(String.format("%s " + DEFAULT_FREQUENCY, nature));
        return dictWord;
    }

}