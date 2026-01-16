"use client";
import {
  ChangeEvent,
  KeyboardEvent,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";

const presetColors = ["#7C3AED", "#0EA5E9", "#F97316", "#16A34A", "#EC4899"];

const clamp = (value: number, min: number, max: number) =>
  Math.min(Math.max(value, min), max);

const hexToRgb = (hex: string) => {
  const sanitized = hex.replace("#", "");
  const red = parseInt(sanitized.slice(0, 2), 16);
  const green = parseInt(sanitized.slice(2, 4), 16);
  const blue = parseInt(sanitized.slice(4, 6), 16);
  return { r: red, g: green, b: blue };
};

const rgbToHex = (r: number, g: number, b: number) =>
  `#${r.toString(16).padStart(2, "0")}${g.toString(16).padStart(2, "0")}${b
    .toString(16)
    .padStart(2, "0")}`.toUpperCase();

const adjustColor = (hex: string, percent: number) => {
  const { r, g, b } = hexToRgb(hex);
  const offset = Math.round(255 * percent);
  return rgbToHex(
    clamp(r + offset, 0, 255),
    clamp(g + offset, 0, 255),
    clamp(b + offset, 0, 255),
  );
};

const getLuminance = (hex: string) => {
  const { r, g, b } = hexToRgb(hex);
  return (0.299 * r + 0.587 * g + 0.114 * b) / 255;
};

const getRgbString = (hex: string, alpha = 1) => {
  const { r, g, b } = hexToRgb(hex);
  return `rgba(${r}, ${g}, ${b}, ${alpha})`;
};

const normalizeHex = (value: string) => {
  const sanitized = value.trim().replace(/^#/, "");
  return `#${sanitized}`.toUpperCase();
};

const derivePalette = (base: string) => {
  const normalized = normalizeHex(base);
  const luminance = getLuminance(normalized);
  const isDark = luminance < 0.5;
  const button = adjustColor(normalized, isDark ? 0.25 : -0.25);
  const shadowSource = adjustColor(normalized, -0.45);
  const textColor = isDark ? "#F8FAFC" : "#0F172A";
  const rgb = hexToRgb(normalized);
  const opposite = rgbToHex(255 - rgb.r, 255 - rgb.g, 255 - rgb.b);
  return {
    card: normalized,
    button,
    text: textColor,
    buttonText: textColor,
    shadow: getRgbString(shadowSource, 0.45),
    opposite,
  };
};

type CounterCardProps = {
  initialColor?: string;
};

export default function CounterCard({ initialColor }: CounterCardProps) {
  const [title, setTitle] = useState("Counter");
  const [isEditingTitle, setIsEditingTitle] = useState(false);
  const titleInputRef = useRef<HTMLInputElement>(null);
  const [count, setCount] = useState(0);
  const [baseColor, setBaseColor] = useState(() =>
    normalizeHex(initialColor ?? presetColors[0]),
  );
  const [pickerOpen, setPickerOpen] = useState(false);
  const [pickerMounted, setPickerMounted] = useState(pickerOpen);

  const palette = useMemo(() => derivePalette(baseColor), [baseColor]);

  const updateBaseColor = (value: string) => {
    setBaseColor(normalizeHex(value));
  };
  const handlePreset = (color: string) => updateBaseColor(color);

  const handleColorInput = (event: ChangeEvent<HTMLInputElement>) =>
    updateBaseColor(event.target.value);

  const openPicker = () => {
    setPickerMounted(true);
    setPickerOpen(true);
  };

  const closePicker = () => setPickerOpen(false);

  const togglePickerOpen = () => (pickerOpen ? closePicker() : openPicker());

  useEffect(() => {
    if (pickerOpen) {
      return;
    }

    const timeout = setTimeout(() => setPickerMounted(false), 180);
    return () => clearTimeout(timeout);
  }, [pickerOpen]);

  const oppositeTextColor =
    getLuminance(palette.opposite) < 0.5 ? "#F8FAFC" : "#0F172A";

  useEffect(() => {
    if (isEditingTitle) {
      titleInputRef.current?.focus();
      titleInputRef.current?.select();
    }
  }, [isEditingTitle]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) =>
    setTitle(event.target.value);

  const handleTitleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      event.preventDefault();
      titleInputRef.current?.blur();
      setIsEditingTitle(false);
    }
    if (event.key === "Escape") {
      event.preventDefault();
      setIsEditingTitle(false);
    }
  };

  return (
    <div
      className="relative group p-4 flex flex-col gap-4 items-center w-full max-w-2xs rounded-2xl text-center overflow-visible"
      style={{ backgroundColor: palette.card }}
    >
      <button
        type="button"
        onClick={togglePickerOpen}
        aria-pressed={pickerOpen}
        className="absolute top-3 right-3 hidden items-center justify-center gap-1 rounded-full border border-white/40 px-3 py-1 text-xs font-semibold uppercase tracking-wide text-white transition duration-150 group-hover:flex"
        style={{
          backgroundColor: palette.button,
          color: palette.buttonText,
          boxShadow: `0 8px 20px ${palette.shadow}`,
        }}
      >
        <span aria-hidden>🎨</span>
        <span className="sr-only">Toggle color picker</span>
      </button>
      <input
        ref={titleInputRef}
        type="text"
        value={title}
        onChange={handleTitleChange}
        onKeyDown={handleTitleKeyDown}
        onBlur={() => setIsEditingTitle(false)}
        onDoubleClick={() => setIsEditingTitle(true)}
        readOnly={!isEditingTitle}
        className="text-2xl font-bold w-full border-b-2 pb-2 bg-transparent text-center outline-none focus-visible:outline-none"
        style={{
          color: palette.text,
          borderColor: adjustColor(palette.card, 0.3),
        }}
        aria-label="Counter title"
      />
      <p
        className="text-6xl font-bold h-20 flex items-center justify-center w-full"
        style={{ color: palette.text }}
      >
        {count}
      </p>
      <div className="flex gap-3">
        <button
          type="button"
          onClick={() => setCount((prev) => prev - 1)}
          className="w-12 h-12 rounded-2xl text-2xl font-semibold transition-all duration-150 active:translate-y-0.5 shadow-lg"
          style={{
            backgroundColor: palette.button,
            color: palette.buttonText,
            boxShadow: `0 14px 28px ${palette.shadow}`,
          }}
        >
          -
        </button>
        <button
          type="button"
          onClick={() => setCount(0)}
          className="w-12 h-12 rounded-2xl text-2xl font-semibold transition-all duration-150 active:translate-y-0.5 shadow-lg"
          style={{
            backgroundColor: palette.button,
            color: palette.buttonText,
            boxShadow: `0 14px 28px ${palette.shadow}`,
          }}
        >
          0
        </button>
        <button
          type="button"
          onClick={() => setCount((prev) => prev + 1)}
          className="w-12 h-12 rounded-2xl text-2xl font-semibold transition-all duration-150 active:translate-y-0.5 shadow-lg"
          style={{
            backgroundColor: palette.button,
            color: palette.buttonText,
            boxShadow: `0 14px 28px ${palette.shadow}`,
          }}
        >
          +
        </button>
      </div>
      {pickerMounted && (
        <div
          className={`color-picker-panel absolute left-0 top-full z-10 mt-3 flex w-full flex-col items-center gap-3 rounded-3xl border border-white/20 bg-white/5 p-4 text-center shadow-[0_16px_40px_rgba(15,23,42,0.55)] backdrop-blur ${
            pickerOpen
              ? "picker-panel-open pointer-events-auto"
              : "picker-panel-closed pointer-events-none"
          }`}
          style={{ backgroundColor: adjustColor(palette.card, -0.08) }}
        >
          <p
            className="text-xs font-semibold tracking-[0.25em] uppercase"
            style={{ color: palette.text }}
          >
            Choose a base color
          </p>
          <div className="flex flex-wrap justify-center gap-2">
            {presetColors.map((color) => (
              <button
                key={color}
                type="button"
                onClick={() => handlePreset(color)}
                className={`h-10 w-10 rounded-full border-2 transition-all duration-150 focus-visible:outline-2 focus-visible:outline-offset-2 ${
                  baseColor === color
                    ? "border-white shadow-[0_0_0_3px_rgba(255,255,255,0.6)]"
                    : "border-white/60 hover:border-white"
                }`}
                style={{ backgroundColor: color }}
              />
            ))}
          </div>
          <div className="flex w-full items-center justify-center gap-4">
            <label
              htmlFor="color-picker"
              className="relative flex h-16 w-full max-w-sm cursor-pointer items-center justify-center rounded-[1.75rem] border border-white/30 px-4 text-center text-sm font-semibold uppercase tracking-wide transition-all duration-150 hover:scale-[1.01]"
              style={{
                backgroundColor: palette.opposite,
                color: oppositeTextColor,
                boxShadow: `0 16px 32px ${palette.shadow}`,
              }}
            >
              <span className="pointer-events-none">Pick color</span>
              <input
                id="color-picker"
                type="color"
                value={baseColor}
                onChange={handleColorInput}
                aria-label="Pick a base color"
                className="absolute inset-0 h-full w-full opacity-0 rounded-[1.75rem]"
              />
            </label>
          </div>
        </div>
      )}
      <style jsx>{`
        .color-picker-panel {
          transform-origin: center;
        }
        .picker-panel-open {
          animation: pickerExpand 240ms ease-out forwards;
        }
        .picker-panel-closed {
          animation: pickerRetract 200ms ease-in forwards;
        }
        @keyframes pickerExpand {
          0% {
            opacity: 0;
            transform: translateY(10px) scale(0.96);
          }
          100% {
            opacity: 1;
            transform: translateY(0) scale(1);
          }
        }
        @keyframes pickerRetract {
          0% {
            opacity: 1;
            transform: translateY(0px) scale(1);
          }
          100% {
            opacity: 0;
            transform: translateY(8px) scale(0.95);
          }
        }
      `}</style>
    </div>
  );
}
