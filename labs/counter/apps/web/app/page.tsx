"use client";
import { useCallback, useRef, useState } from "react";
import CounterCard from "@/components/CounterCard";
import NewCounter from "@/components/NewCounter";

const presetColors = ["#7C3AED", "#0EA5E9", "#F97316", "#16A34A", "#EC4899"];

type CounterConfig = {
  id: number;
  color: string;
};

const initialCounterConfigs: CounterConfig[] = presetColors.map(
  (color, idx) => ({
    id: idx + 1,
    color,
  }),
);

export default function Home() {
  const [counterConfigs, setCounterConfigs] = useState<CounterConfig[]>(
    () => initialCounterConfigs,
  );
  const nextIdRef = useRef(initialCounterConfigs.length + 1);
  const nextColorIndexRef = useRef(initialCounterConfigs.length);
  const [removingIds, setRemovingIds] = useState<Set<number>>(() => new Set());

  const addCounter = useCallback(() => {
    setCounterConfigs((prev) => {
      const nextColor =
        presetColors[nextColorIndexRef.current % presetColors.length];
      const nextConfig = {
        id: nextIdRef.current,
        color: nextColor,
      };
      nextIdRef.current += 1;
      nextColorIndexRef.current += 1;
      return [...prev, nextConfig];
    });
  }, []);

  const startRemovingCounter = useCallback((id: number) => {
    setRemovingIds((prev) => {
      if (prev.has(id)) return prev;
      const next = new Set(prev);
      next.add(id);
      return next;
    });
  }, []);

  const finishRemovingCounter = useCallback((id: number) => {
    setRemovingIds((prev) => {
      if (!prev.has(id)) return prev;
      const next = new Set(prev);
      next.delete(id);
      return next;
    });
    setCounterConfigs((prev) => prev.filter((config) => config.id !== id));
  }, []);

  return (
    <div>
      <main className="w-3/4 max-w-5xl mx-auto grid grid-cols-[repeat(auto-fit,minmax(250px,1fr))] gap-6 justify-items-center">
        {counterConfigs.map((config) => (
          <CounterCard
            key={config.id}
            initialColor={config.color}
            onDelete={() => startRemovingCounter(config.id)}
            isRemoving={removingIds.has(config.id)}
            onRemoveAnimationEnd={() => finishRemovingCounter(config.id)}
          />
        ))}
        <NewCounter onAdd={addCounter} />
      </main>
      <footer className="text-center text-sm text-gray-500 mt-14 mb-8">
        <p>© 2026 Multi-Counter. All rights reserved.</p>
        <p className="flex items-center justify-center gap-2">
          <a
            href="https://github.com/atin-roy"
            target="_blank"
            rel="noopener noreferrer"
          >
            GitHub
          </a>
          -
          <a
            href="https://leetcode.com/u/AtinRoy/"
            target="_blank"
            rel="noopener noreferrer"
          >
            Leetcode
          </a>
        </p>
      </footer>
    </div>
  );
}
