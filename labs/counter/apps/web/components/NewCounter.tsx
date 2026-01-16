"use client";
import { useMemo, useState } from "react";

const CARD_BG = "#E5E7EB"; // gray-200
const CIRCLE_BG = "#F8FAFC"; // soft white
const PLUS_COLOR = "#1F2933"; // dark slate

type NewCounterProps = {
  onAdd?: () => void;
};

export default function NewCounter({ onAdd }: NewCounterProps) {
  const [pressed, setPressed] = useState(false);

  const styles = useMemo(
    () => ({
      card: {
        backgroundColor: CARD_BG,
      },
      circle: {
        backgroundColor: CIRCLE_BG,
        color: PLUS_COLOR,
        boxShadow: pressed
          ? "0 8px 18px rgba(15, 23, 42, 0.25)"
          : "0 22px 55px rgba(15, 23, 42, 0.35), 0 10px 20px rgba(15, 23, 42, 0.25)",
      },
    }),
    [pressed],
  );

  return (
    <div
      className="relative group p-4 flex items-center justify-center w-full max-w-2xs rounded-2xl overflow-hidden h-58"
      style={styles.card}
    >
      <div
        role="button"
        aria-label="Add counter"
        onClick={() => onAdd?.()}
        onMouseDown={() => setPressed(true)}
        onMouseUp={() => setPressed(false)}
        onMouseLeave={() => setPressed(false)}
        className={`flex items-center justify-center w-28 h-28 rounded-full
          text-8xl font-semibold select-none cursor-pointer
          transition-all duration-150 ease-out
          ${pressed ? "scale-90" : "scale-100 group-hover:scale-105"}`}
        style={styles.circle}
      >
        +
      </div>
    </div>
  );
}
