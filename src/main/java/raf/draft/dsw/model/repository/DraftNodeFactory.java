package raf.draft.dsw.model.repository;

import raf.draft.dsw.model.nodes.DraftNode;

public interface DraftNodeFactory {
    DraftNode createNode(Integer id, String... parameters);
}
