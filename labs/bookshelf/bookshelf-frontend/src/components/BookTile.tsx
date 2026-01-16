interface BookTileProps {
  title: string;
  author: string;
  description?: string;
}

export default function BookTile({
  title,
  author,
  description,
}: BookTileProps) {
  return (
    <div className="bg-background/50 backdrop-blur-sm border rounded-lg p-4">
      <h2 className="text-lg font-bold">{title}</h2>
      <p className="text-sm text-muted-foreground tracking-tight">{author}</p>
      {description && (
        <p className="mt-2 text-sm text-muted-foreground/80">{description}</p>
      )}
    </div>
  );
}
