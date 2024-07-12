package edu.school21.spring.renderer;

import edu.school21.spring.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer {
    private final PreProcessor processor;

    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void renderer(String message) {
        System.err.println(processor.process(message));
    }
}
