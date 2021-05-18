package editor.clipboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {
    private List<ClipboardObserver> clipboardObservers;
    Stack<String> texts;

    public ClipboardStack() {
        texts = new Stack<>();
        clipboardObservers = new ArrayList<>();
    }

    public String pop() {
        String s = texts.pop();
        notifyClipboardObservers();
        return s;
    }

    public String push(String text) {
        String s = texts.push(text);
        notifyClipboardObservers();
        return s;
    }

    public String peek() {
        return texts.peek();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public void clear() {
        texts.clear();
        notifyClipboardObservers();
    }

    public boolean addClipboardObserver(ClipboardObserver o) {
        return this.clipboardObservers.add(o);
    }

    public boolean removeClipboardObserver(ClipboardObserver o) {
        return this.clipboardObservers.remove(o);
    }

    private void notifyClipboardObservers() {
        clipboardObservers.forEach(o -> o.updateClipboard());
    }
}
